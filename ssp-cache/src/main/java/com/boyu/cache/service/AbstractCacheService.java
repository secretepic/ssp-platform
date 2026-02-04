package com.boyu.cache.service;

import com.boyu.cache.CacheService;
import com.boyu.entity.BaseEntity;
import jakarta.annotation.Resource;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Optional;

public abstract class AbstractCacheService<T extends BaseEntity> {

    @Resource
    protected CacheService cacheService;

    abstract String getCacheKey(String val);

    abstract T getLocal(String key);

    abstract void putLocal(String key, T val);

    abstract void delLocal(String key);

    public T get(String key, Class<T> type) {
        Optional<T> cache = cacheService.get(key, type);
        if (cache.isPresent()) {
            putLocal(key, cache.get());
            return cache.get();
        }
        return null;
    }

    public void set(String key, T val, Duration duration) {
        cacheService.set(key, val.toString(), duration);
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
