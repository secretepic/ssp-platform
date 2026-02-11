package com.boyu.cache.service;

import com.boyu.entity.BaseEntity;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.google.common.util.concurrent.RateLimiter;
import java.time.Duration;

public class GenericCacheService<T extends BaseEntity> extends AbstractCacheService<T> {

    // 差异化配置：缓存前缀
    private final String cachePrefix;
    // Caffeine缓存核心对象
    private final Cache<String, T> caffeineCache;
    // 缓存实体类类型（用于构建空对象）
    private final Class<T> entityClass;
    // 缓存null key（1分钟），用于防止缓存击穿
    private final Cache<String, RateLimiter> rateLimiterCache;
    /**
     * 构造方法：注入差异化配置，初始化缓存
     * @param cachePrefix 缓存key前缀（RedisPrefix.XXX.getPrefix()）
     * @param expireMinutes 本地缓存过期分钟数
     * @param maxSize 本地缓存最大容量
     * @param entityClass 实体类类型（用于构建空对象防击穿）
     */
    public GenericCacheService(String cachePrefix, int expireMinutes, int maxSize, Class<T> entityClass) {
        this.cachePrefix = cachePrefix;
        // 初始化Caffeine缓存（复用原逻辑）
        this.caffeineCache = Caffeine.newBuilder()
                .expireAfterWrite(Duration.ofMinutes(expireMinutes))
                .maximumSize(maxSize)
                .build();
        this.entityClass = entityClass;
        // 初始化null key缓存（1分钟过期）
        this.rateLimiterCache = Caffeine.newBuilder()
                .expireAfterWrite(Duration.ofMinutes(1))
                .maximumSize(maxSize)
                .build();
    }

     /**
     * 统一实现：获取实体类类型
     */
    @Override
    public Class<T> getEntityClass() {
        return entityClass;
    }

     /**
     * 统一实现：是否查询Redis
     */
    @Override
    public boolean canQueryRedis(String key) {
        // 原子性获取/创建限流器：不存在则执行lambda创建，避免并发重复
        RateLimiter rateLimiter = rateLimiterCache.get(key, k -> RateLimiter.create(1.0));
        // 无等待尝试获取令牌：1秒仅1个请求能通过，其余直接返回false
        return rateLimiter.tryAcquire();
    }

    /**
     * 统一实现：拼接缓存key（前缀+入参）
     */
    @Override
    public String getCacheKey(String val) {
        return cachePrefix + val;
    }

    /**
     * 统一实现：获取缓存前缀
     */
    @Override
    public String getCachePrefix() {
        return cachePrefix;
    }


    /**
     * 统一实现：本地缓存查询+防击穿逻辑
     */
    @Override
    public T getLocal(String key) {
        return caffeineCache.getIfPresent(key);
    }


    /**
     * 统一实现：本地缓存写入
     */
    @Override
    public void putLocal(String key, T val) {
        if (val == null) {
            return;
        }
        caffeineCache.put(key, val);
    }

    /**
     * 统一实现：本地缓存删除
     */
    @Override
    public void delLocal(String key) {
        caffeineCache.invalidate(key);
    }

}
