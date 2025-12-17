package com.boyu.cache.impl;

import com.alibaba.fastjson2.JSON;
import com.boyu.cache.CacheService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.Assert;

import java.time.Duration;
import java.util.Optional;

/**
 * 基于非阻塞Redis的缓存实现
 */
@Slf4j
public class RedisCacheServiceImpl implements CacheService {
    
    private final RedisTemplate<String, String> redisTemplate;
    
    public RedisCacheServiceImpl(RedisTemplate<String, String> redisTemplate) {
        Assert.notNull(redisTemplate, "RedisTemplate must not be null");
        this.redisTemplate = redisTemplate;
    }
    
    @Override
    public <T> Optional<T> get(String key, Class<T> type) {
        try {
            String value = redisTemplate.opsForValue().get(key);
            if (value == null) {
                return Optional.empty();
            }
            return Optional.ofNullable(JSON.parseObject(value, type));
        } catch (Exception e) {
            log.error("Failed to deserialize cache value for key: {} {}", key, e.getLocalizedMessage());
            return Optional.empty();
        }
    }
    
    @Override
    public void set(String key, String value, Duration duration) {
        try {
            redisTemplate.opsForValue().set(key, value, duration);
        } catch (Exception e) {
            log.error("Failed to serialize cache value for key: {} {}", key, e.getLocalizedMessage());
        }
    }

     @Override
    public void set(String key, String value) {
         try {
             String jsonValue = JSON.toJSONString(value);
             redisTemplate.opsForValue().set(key, jsonValue);
         } catch (Exception e) {
             log.error("Failed to serialize cache value for key: {} {}", key, e.getLocalizedMessage());
         }
    }
    
    @Override
    public void delete(String key) {
        redisTemplate.delete(key);
    }
    
    @Override
    public boolean exists(String key) {
        return redisTemplate.hasKey(key);
    }
    
    @Override
    public boolean expire(String key, Duration duration) {
        return Boolean.TRUE.equals(redisTemplate.expire(key, duration));
    }
    
    @Override
    public Long getExpire(String key) {
        return redisTemplate.getExpire(key);
    }

}