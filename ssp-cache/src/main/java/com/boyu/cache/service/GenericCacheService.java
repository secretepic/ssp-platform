package com.boyu.cache.service;

import com.boyu.entity.BaseEntity;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import java.time.Duration;

public class GenericCacheService<T extends BaseEntity> extends AbstractCacheService<T> {

    // 差异化配置：缓存前缀
    private final String cachePrefix;
    // Caffeine缓存核心对象
    private final Cache<String, T> caffeineCache;
    // 缓存实体类类型（用于构建空对象）
    private final Class<T> entityClass;

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
    }

    /**
     * 统一实现：拼接缓存key（前缀+入参）
     */
    @Override
    String getCacheKey(String val) {
        return cachePrefix + val;
    }


    /**
     * 统一实现：本地缓存查询+防击穿逻辑
     */
    @Override
    T getLocal(String key) {
        T entity = caffeineCache.getIfPresent(key);
        if (entity == null) {
            try {
                // 反射构建空实体（替代硬编码的Builder，适配所有BaseEntity子类）
                entity = entityClass.getDeclaredConstructor().newInstance();
            } catch (Exception e) {
                // 兼容Builder模式（若实体用Builder构建）
                throw new RuntimeException("构建空缓存实体失败", e);
            }
            // 防打穿：存入空实体（原逻辑不变）
            caffeineCache.put(key, entity);
        }
        return entity;
    }


    /**
     * 统一实现：本地缓存写入
     */
    @Override
    void putLocal(String key, T val) {
        if (val == null) {
            return;
        }
        caffeineCache.put(key, val);
    }

    /**
     * 统一实现：本地缓存删除
     */
    @Override
    void delLocal(String key) {
        caffeineCache.invalidate(key);
    }

}
