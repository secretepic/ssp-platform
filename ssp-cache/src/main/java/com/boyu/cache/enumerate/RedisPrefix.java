package com.boyu.cache.enumerate;

public enum RedisPrefix {
    USER_INFO("ssp:user:info:");

    private final String prefix;


    RedisPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getPrefix() {
        return prefix;
    }

    public RedisPrefix getRedisPrefix(String val) {
        return RedisPrefix.valueOf(val);
    }
}
