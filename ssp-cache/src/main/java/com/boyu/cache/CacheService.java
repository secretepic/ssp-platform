package com.boyu.cache;

import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Optional;

/**
 * 缓存服务抽象接口
 */
public interface CacheService {
    
    // ---------------- 非响应式方法 ----------------
    
    /**
     * 获取缓存值（非响应式）
     * @param key 缓存键
     * @param type 缓存值类型
     * @param <T> 缓存值泛型类型
     * @return 缓存值，可能为空
     */
    default <T> Optional<T> get(String key, Class<T> type) {
        throw new UnsupportedOperationException("get is not supported");
    }
    
    /**
     * 设置缓存值（非响应式）
     * @param key 缓存键
     * @param value 缓存值
     * @param duration 过期时间
     */
    default void set(String key, String value, Duration duration) {
        throw new UnsupportedOperationException("set is not supported");
    }

    /**
     * 设置缓存值（非响应式）
     * @param key 缓存键
     * @param value 缓存值
     */
    default void set(String key, String value) {
        throw new UnsupportedOperationException("set is not supported");
    }
    
    /**
     * 删除缓存（非响应式）
     * @param key 缓存键
     */
    default void delete(String key) {
        throw new UnsupportedOperationException("delete is not supported");
    }
    
    /**
     * 检查缓存键是否存在（非响应式）
     * @param key 缓存键
     * @return 是否存在
     */
    default boolean exists(String key) {
        throw new UnsupportedOperationException("exists is not supported");
    }
    
    /**
     * 设置缓存过期时间（非响应式）
     * @param key 缓存键
     * @param duration 过期时间
     * @return 是否设置成功
     */
    default boolean expire(String key, Duration duration) {
        throw new UnsupportedOperationException("expire is not supported");
    }
    
    /**
     * 获取缓存过期时间（非响应式）
     * @param key 缓存键
     * @return 过期时间，单位秒
     */
    default Long getExpire(String key) {
        throw new UnsupportedOperationException("getExpire is not supported");
    }
    
    // ---------------- 响应式方法 ----------------
    
    /**
     * 获取缓存值（响应式）
     * @param key 缓存键
     * @param type 缓存值类型
     * @param <T> 缓存值泛型类型
     * @return 响应式结果，包含缓存值
     */
    default <T> Mono<Optional<T>> getReactive(String key, Class<T> type) {
        throw new UnsupportedOperationException("getReactive is not supported");
    }
    
    /**
     * 设置缓存值（响应式）
     * @param key 缓存键
     * @param value 缓存值
     * @param duration 过期时间
     * @param <T> 缓存值泛型类型
     * @return 响应式结果
     */
    default <T> Mono<Void> setReactive(String key, T value, Duration duration) {
        throw new UnsupportedOperationException("setReactive is not supported");
    }

     /**
     * 设置缓存值（响应式）
     * @param key 缓存键
     * @param value 缓存值
     * @param <T> 缓存值泛型类型
     * @return 响应式结果
     */
    default <T> Mono<Void> setReactive(String key, T value) {
        throw new UnsupportedOperationException("setReactive is not supported");
    }
    
    /**
     * 删除缓存（响应式）
     * @param key 缓存键
     * @return 响应式结果
     */
    default Mono<Void> deleteReactive(String key) {
        throw new UnsupportedOperationException("deleteReactive is not supported");
    }
    
    /**
     * 检查缓存键是否存在（响应式）
     * @param key 缓存键
     * @return 响应式结果，包含存在性
     */
    default Mono<Boolean> existsReactive(String key) {
        throw new UnsupportedOperationException("existsReactive is not supported");
    }
    
    /**
     * 设置缓存过期时间（响应式）
     * @param key 缓存键
     * @param duration 过期时间
     * @return 响应式结果，包含是否设置成功
     */
    default Mono<Boolean> expireReactive(String key, Duration duration) {
        throw new UnsupportedOperationException("expireReactive is not supported");
    }
    
    /**
     * 获取缓存过期时间（响应式）
     * @param key 缓存键
     * @return 响应式结果，包含过期时间
     */
    default Mono<Duration> getExpireReactive(String key) {
        throw new UnsupportedOperationException("getExpireReactive is not supported");
    }
}