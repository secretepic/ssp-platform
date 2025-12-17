package com.boyu.cache.impl;

import com.alibaba.fastjson2.JSON;
import com.boyu.cache.CacheService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.util.Assert;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Optional;

/**
 * 基于响应式Redis的缓存实现
 */
@Slf4j
public class ReactiveRedisCacheServiceImpl implements CacheService {

    private final ReactiveRedisTemplate<String, String> reactiveRedisTemplate;

    public ReactiveRedisCacheServiceImpl(ReactiveRedisTemplate<String, String> reactiveRedisTemplate) {
        Assert.notNull(reactiveRedisTemplate, "ReactiveRedisTemplate must not be null");
        this.reactiveRedisTemplate = reactiveRedisTemplate;
    }

    // ---------------- 响应式方法实现 ----------------

    @Override
    public <T> Mono<Optional<T>> getReactive(String key, Class<T> type) {
        return reactiveRedisTemplate.opsForValue().get(key)
                .map(value -> {
                    try {
                        return Optional.ofNullable(JSON.parseObject(value, type));
                    } catch (Exception e) {
                        log.error("Failed to deserialize cache value for key: {}", key, e);
                        return Optional.<T>empty();
                    }
                })
                .defaultIfEmpty(Optional.empty());
    }

    @Override
    public <T> Mono<Void> setReactive(String key, T value, Duration duration) {
        try {
            String jsonValue = JSON.toJSONString(value);
            return reactiveRedisTemplate.opsForValue().set(key, jsonValue, duration)
                    .then();
        } catch (Exception e) {
            log.error("Failed to serialize cache value for key: {}", key, e);
            return Mono.error(e);
        }
    }

     @Override
    public <T> Mono<Void> setReactive(String key, T value) {
        try {
            String jsonValue = JSON.toJSONString(value);
            return reactiveRedisTemplate.opsForValue().set(key, jsonValue)
                    .then();
        } catch (Exception e) {
            log.error("Failed to serialize cache value for key: {}", key, e);
            return Mono.error(e);
        }
    }

    @Override
    public Mono<Void> deleteReactive(String key) {
        return reactiveRedisTemplate.delete(key)
                .then();
    }

    @Override
    public Mono<Boolean> existsReactive(String key) {
        return reactiveRedisTemplate.hasKey(key);
    }

    @Override
    public Mono<Boolean> expireReactive(String key, Duration duration) {
        return reactiveRedisTemplate.expire(key, duration);
    }

    @Override
    public Mono<Duration> getExpireReactive(String key) {
        return reactiveRedisTemplate.getExpire(key);
    }
}