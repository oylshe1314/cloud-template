package com.sk.op.application.redis.config;

import com.sk.op.application.utils.NameUtils;

public interface RedisKey {

    default String module() {
        String name = this.getClass().getSimpleName();
        if (name.endsWith("RedisKey")) {
            name = name.substring(0, name.length() - 8);
        }
        return NameUtils.toUnderscore(name);
    }

    String name();

    default String key() {
        String key = "op_";
        if (this.module() != null) {
            key += this.module() + "_";
        }
        return key + this.name().toLowerCase();
    }

    default String key(String... suffixes) {
        if (suffixes.length > 0) {
            return key() + "_" + String.join("_", suffixes);
        } else {
            return key();
        }
    }

    default String key(Number... number) {
        if (number.length > 0) {
            String[] suffixes = new String[number.length];
            for (int i = 0; i < number.length; i++) {
                suffixes[i] = number[i].toString();
            }
            return key(suffixes);
        } else {
            return key();
        }
    }

    static String valueOf(String... suffixes) {
        return String.join("_", suffixes);
    }

    static String valueOf(Number... number) {
        String[] suffixes = new String[number.length];
        for (int i = 0; i < number.length; i++) {
            suffixes[i] = number[i].toString();
        }
        return valueOf(suffixes);
    }
}
