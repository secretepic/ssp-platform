package com.boyu.cache.service;

import com.boyu.cache.enumerate.RedisPrefix;
import com.boyu.entity.system.UserEntity;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class UserCacheService extends AbstractCacheService<UserEntity> {

    private final Cache<String, UserEntity> caffeineCache = Caffeine.newBuilder()
            .expireAfterWrite(Duration.ofMinutes(10))
            .maximumSize(1000)
            .build();

    @Override
    String getCacheKey(String val) {
        return RedisPrefix.USER_INFO.getPrefix() + val;
    }

    @Override
    public UserEntity getLocal(String key) {
        UserEntity user = caffeineCache.getIfPresent(key);
        if (user == null) {
            user = UserEntity.builder().build();
            // 防打穿,可以手动put本地和redis
            caffeineCache.put(key, user);
        }
        return user;
    }

    @Override
    public void putLocal(String key, UserEntity val) {
        if (val == null) {
            return;
        }
        caffeineCache.put(key, val);
    }
}
