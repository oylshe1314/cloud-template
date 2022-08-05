package com.sk.op.application.redis.component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.StringWriter;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class RedisUtils {

    private final StringRedisTemplate stringRedisTemplate;
    private final ObjectMapper objectMapper;

    public RedisUtils(StringRedisTemplate stringRedisTemplate, ObjectMapper objectMapper) {
        this.stringRedisTemplate = stringRedisTemplate;
        this.objectMapper = objectMapper;
    }

    public <T> T fromJson(String json, Class<T> tClass) {
        if (!StringUtils.hasText(json)) {
            return null;
        }
        try {
            return objectMapper.readValue(json, tClass);
        } catch (JsonProcessingException e) {
            log.error("Read value from json error", e);
            return null;
        }
    }

    public <T> T fromJson(String json, TypeReference<T> typeReference) {
        if (!StringUtils.hasText(json)) {
            return null;
        }
        try {
            return objectMapper.readValue(json, typeReference);
        } catch (JsonProcessingException e) {
            log.error("Read value from json error", e);
            return null;
        }
    }

    public <T> String toJson(T value) {
        if (value == null) {
            return "";
        }
        try {
            StringWriter writer = new StringWriter();
            objectMapper.writeValue(writer, value);
            return writer.toString();
        } catch (IOException e) {
            log.error("Read value from json error", e);
            return "";
        }
    }

    public String lock(String key) {
        return lock(key, 10);
    }

    private String lockerKey(String key) {
        if (!key.contains("lock")) {
            key += "_locker";
        }
        return key;
    }

    public String lock(String key, long timeout) {
        Boolean r;
        key = lockerKey(key);
        String value = UUID.randomUUID().toString();
        for (; ; ) {
            r = stringRedisTemplate.opsForValue().setIfAbsent(key, value, timeout, TimeUnit.SECONDS);
            if ((Boolean.TRUE.equals(r))) {
                return value;
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                log.error("Thread sleep error", e);
                return null;
            }
        }
    }

    public String tryLock(String key) {
        return tryLock(key, 10);
    }

    public String tryLock(String key, long timeout) {
        key = lockerKey(key);
        String value = UUID.randomUUID().toString();
        Boolean r = stringRedisTemplate.opsForValue().setIfAbsent(key, value, timeout, TimeUnit.SECONDS);
        return Boolean.TRUE.equals(r) ? value : null;
    }

    public void unlock(String key, String value) {
        key = lockerKey(key);
        String cache = stringRedisTemplate.opsForValue().get(key);
        if (value.equals(cache)) {
            stringRedisTemplate.delete(key);
        }
    }

    public void set(String key, String value) {
        stringRedisTemplate.opsForValue().set(key, value);
    }

    public void set(String key, String value, long timeout) {
        stringRedisTemplate.opsForValue().set(key, value, timeout, TimeUnit.SECONDS);
    }

    public void set(String key, String value, long timeout, TimeUnit timeUnit) {
        stringRedisTemplate.opsForValue().set(key, value, timeout, timeUnit);
    }

    public void set(String key, Object value) {
        stringRedisTemplate.opsForValue().set(key, toJson(value));
    }

    public void set(String key, Object value, long timeout) {
        stringRedisTemplate.opsForValue().set(key, toJson(value), timeout, TimeUnit.SECONDS);
    }

    public void set(String key, Object value, long timeout, TimeUnit timeUnit) {
        stringRedisTemplate.opsForValue().set(key, toJson(value), timeout, timeUnit);
    }

    public String get(String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }

    public <T> T get(String key, Class<T> tClass) {
        return fromJson(this.get(key), tClass);
    }

    public void expireAt(String key, Date expireTime) {
        stringRedisTemplate.expireAt(key, expireTime);
    }

    public void expire(String key, long timeout) {
        stringRedisTemplate.expire(key, timeout, TimeUnit.SECONDS);
    }

    public void expire(String key, long timeout, TimeUnit timeUnit) {
        stringRedisTemplate.expire(key, timeout, timeUnit);
    }

    public Boolean del(String key) {
        return stringRedisTemplate.delete(key);
    }

    public Long incr(String key, long delta) {
        return stringRedisTemplate.opsForValue().increment(key, delta);
    }

    public Long incrBy(String key, long delta) {
        return stringRedisTemplate.opsForValue().increment(key, delta);
    }

    public Long decr(String key, long delta) {
        return stringRedisTemplate.opsForValue().decrement(key, delta);
    }

    public Long decrBy(String key, long delta) {
        return stringRedisTemplate.opsForValue().decrement(key, delta);
    }

    public void hSet(String key, String field, String value) {
        BoundHashOperations<String, String, String> operation = stringRedisTemplate.boundHashOps(key);
        operation.put(field, value);
    }

    public void hSet(String key, String field, Object value) {
        BoundHashOperations<String, String, String> operation = stringRedisTemplate.boundHashOps(key);
        operation.put(field, toJson(value));
    }

    public void hmSet(String key, Map<String, String> fields) {
        BoundHashOperations<String, String, String> operation = stringRedisTemplate.boundHashOps(key);
        operation.putAll(fields);
    }

    public String hGet(String key, String field) {
        BoundHashOperations<String, String, String> operation = stringRedisTemplate.boundHashOps(key);
        return operation.get(field);
    }

    public <T> T hGet(String key, String field, Class<T> tClass) {
        BoundHashOperations<String, String, String> operation = stringRedisTemplate.boundHashOps(key);
        return fromJson(operation.get(field), tClass);
    }

    public List<String> hmGet(String key, Collection<String> fields) {
        BoundHashOperations<String, String, String> operation = stringRedisTemplate.boundHashOps(key);
        return operation.multiGet(fields);
    }

    public Long hIncrBy(String key, String field, long delta) {
        BoundHashOperations<String, String, String> operation = stringRedisTemplate.boundHashOps(key);
        return operation.increment(field, delta);
    }

    public Map<String, String> hGetAll(String key) {
        BoundHashOperations<String, String, String> operation = stringRedisTemplate.boundHashOps(key);
        return operation.entries();
    }

    public Long hDel(String key, String... fields) {
        BoundHashOperations<String, String, String> operation = stringRedisTemplate.boundHashOps(key);
        return operation.delete((Object[]) fields);
    }

    public Boolean zAdd(String key, String value, double score) {
        BoundZSetOperations<String, String> operations = stringRedisTemplate.boundZSetOps(key);
        return operations.add(value, score);
    }

    public Double zIncrBy(String key, String value, double score) {
        BoundZSetOperations<String, String> operations = stringRedisTemplate.boundZSetOps(key);
        return operations.incrementScore(value, score);
    }

    public Double zScore(String key, String value) {
        BoundZSetOperations<String, String> operations = stringRedisTemplate.boundZSetOps(key);
        return operations.score(value);
    }

    public Long zRank(String key, String value) {
        BoundZSetOperations<String, String> operations = stringRedisTemplate.boundZSetOps(key);
        return operations.rank(value);
    }

    public Long zReverseRank(String key, String value) {
        BoundZSetOperations<String, String> operations = stringRedisTemplate.boundZSetOps(key);
        return operations.reverseRank(value);
    }

    public Set<String> zReverseRange(String key, int start, int end) {
        BoundZSetOperations<String, String> operations = stringRedisTemplate.boundZSetOps(key);
        return operations.reverseRange(start, end);
    }

    public Map<String, Double> zReverseRangeWithScores(String key, long start, long end) {
        BoundZSetOperations<String, String> operations = stringRedisTemplate.boundZSetOps(key);

        Set<ZSetOperations.TypedTuple<String>> set = operations.reverseRangeWithScores(start, end);
        if (set == null) {
            return null;
        }

        Map<String, Double> map = new LinkedHashMap<>();
        set.forEach(tuple -> map.put(tuple.getValue(), tuple.getScore()));
        return map;
    }

    public Long lPush(String key, String value) {
        BoundListOperations<String, String> operations = stringRedisTemplate.boundListOps(key);
        return operations.leftPush(value);
    }

    public List<String> range(String key, long start, long end) {
        BoundListOperations<String, String> operations = stringRedisTemplate.boundListOps(key);
        return operations.range(start, end);
    }

    public void lTrim(String key, long start, long end) {
        BoundListOperations<String, String> operations = stringRedisTemplate.boundListOps(key);
        operations.trim(start, end);
    }
}
