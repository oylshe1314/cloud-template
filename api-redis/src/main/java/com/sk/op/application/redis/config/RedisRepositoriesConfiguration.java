package com.sk.op.application.redis.config;


import org.springframework.boot.SpringBootConfiguration;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@SpringBootConfiguration
@EnableRedisRepositories(basePackages = "com.sk.op.application.redis.repository")
public class RedisRepositoriesConfiguration {

}
