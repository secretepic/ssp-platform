package com.boyu.cache.service;

import com.alibaba.fastjson2.JSON;
import com.boyu.cache.CacheService;
import com.boyu.entity.BaseEntity;
import jakarta.annotation.Resource;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Optional;

public abstract class AbstractCacheService<T extends BaseEntity> {

    @Resource
    protected CacheService cacheService;

    public abstract String getCacheKey(String val);

    public abstract String getCachePrefix();

    public abstract T getLocal(String key);

    public abstract void putLocal(String key, T val);

    public abstract void delLocal(String key);

    public abstract Class<T> getEntityClass();

    public abstract boolean canQueryRedis(String key);

    public T get(String key, Class<T> type) {
        Optional<T> cache = cacheService.get(key, type);
        if (cache.isPresent()) {
            putLocal(key, cache.get());
            return cache.get();
        }
        return null;
    }

    public void set(String key, T val, Duration... duration) {
        if (duration.length != 0) {
            cacheService.set(key, JSON.toJSONString(val), duration[0]);
        } else {
            cacheService.set(key, JSON.toJSONString(val));
        }
        putLocal(key, val);
    }

    public void del(String key) {
        cacheService.delete(key);
        delLocal(key);
    }
    
    public Mono<Optional<T>> getReactive(String key, Class<T> type) {
        return cacheService.getReactive(key, type);
    }



}
