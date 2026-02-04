package com.boyu.cache.config;

import com.boyu.cache.CacheService;
import com.boyu.cache.impl.ReactiveRedisCacheServiceImpl;
import com.boyu.cache.impl.RedisCacheServiceImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * 缓存自动配置类
 * 根据项目依赖自动选择合适的缓存实现
 */
@Configuration
public class CacheConfig {
    
    /**
     * 基于非阻塞Redis的缓存实现
     * 当RedisTemplate存在时创建
     */
    @ConditionalOnMissingClass("org.springframework.web.reactive.DispatcherHandler")
    @Bean(name = "redisCacheService")
    @Primary
    public CacheService redisCacheService(RedisTemplate<String, String> redisTemplate) {
        return new RedisCacheServiceImpl(redisTemplate);
    }
    
    /**
     * 基于响应式Redis的缓存实现
     * 当ReactiveRedisTemplate存在时创建
     */
    @ConditionalOnClass(name = "org.springframework.web.reactive.DispatcherHandler")
    @Bean(name = "reactiveRedisCacheService")
    public CacheService reactiveRedisCacheService(ReactiveRedisTemplate<String, String> reactiveRedisTemplate) {
        return new ReactiveRedisCacheServiceImpl(reactiveRedisTemplate);
    }
}