package com.boyu.cache.manager;

import cn.hutool.core.util.ObjectUtil;
import com.boyu.cache.service.AbstractCacheService;
import com.boyu.entity.BaseEntity;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.util.CastUtils;
import org.springframework.stereotype.Component;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class CacheManager {

    private final List<AbstractCacheService<? extends BaseEntity>> cacheServiceList;

    private final Map<Class<? extends BaseEntity>, AbstractCacheService<? extends BaseEntity>> cacheServiceMap = new HashMap<>();

    @PostConstruct
    public void initCacheServiceMap() {
        for (AbstractCacheService<? extends BaseEntity> cacheService : cacheServiceList) {
            cacheServiceMap.put(cacheService.getEntityClass(), cacheService);
        }
    }

    public <T extends BaseEntity> T get(String val, Class<T> type) {
        if (ObjectUtil.isAllEmpty(val, type)) {
            log.error("缓存值[{}]或类型[{}]为空，无法获取缓存", val, type.getName());
            return null;
        }
        AbstractCacheService<? extends BaseEntity> abstractCacheService = cacheServiceMap.get(type);
        if (abstractCacheService == null) {
            log.error("缓存服务[{}]未初始化，无法获取缓存值", type.getName());
            return null;
        }
        AbstractCacheService<T> cacheService = CastUtils.cast(abstractCacheService);
        String key = cacheService.getCacheKey(val);
        T local = cacheService.getLocal(key);
        if (local != null) {
            return local;
        }
        if (!cacheService.canQueryRedis(key)) {
            return null;
        }
        return cacheService.get(key, type);
    }

    public <T extends BaseEntity> void set(String val, T entity, Duration... duration) {
        if (ObjectUtil.isAllEmpty(val, entity)) {
            log.error("缓存值[{}]或实体[{}]为空，无法设置缓存", val, entity);
            return;
        }
        AbstractCacheService<? extends BaseEntity> abstractCacheService = cacheServiceMap.get(entity.getClass());
        if (abstractCacheService == null) {
            log.error("缓存服务[{}]未初始化，无法设置缓存值", entity.getClass().getName());
            return;
        }
        AbstractCacheService<T> cacheService = CastUtils.cast(abstractCacheService);
        String key = cacheService.getCacheKey(val);
        cacheService.set(key, entity, duration);
    }


}