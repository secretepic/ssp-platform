package com.boyu.cache.manager;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson2.JSON;
import com.boyu.cache.CacheService;
import com.boyu.cache.service.AbstractCacheService;
import com.boyu.entity.BaseEntity;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.util.CastUtils;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class CacheManager {

    private final CacheService cacheService;

    private final List<AbstractCacheService<? extends BaseEntity>> cacheServiceList;

    private final Map<Class<? extends BaseEntity>, AbstractCacheService<? extends BaseEntity>> cacheServiceMap = new HashMap<>();

    @PostConstruct
    public void initCacheServiceMap() {
        for (AbstractCacheService<? extends BaseEntity> cacheService : cacheServiceList) {
            cacheServiceMap.put(cacheService.getEntityClass(), cacheService);
        }
    }

    public <T> Optional<T> get(String key, Class<T> type) {
        if (ObjectUtil.isAllEmpty(key, type)) {
            log.error("缓存键[{}]或类型[{}]为空，无法获取缓存", key, type.getName());
            return Optional.empty();
        }
        return cacheService.get(key, type);
    }

    public <T> void set(String key, T value, Duration... duration) {
        if (ObjectUtil.isAllEmpty(key, value)) {
            log.error("缓存键[{}]或值[{}]为空，无法设置缓存", key, value);
            return;
        }
        if (duration.length != 0) {
            cacheService.set(key, JSON.toJSONString(value), duration[0]);
        } else {
            cacheService.set(key, JSON.toJSONString(value));
        }
    }

    public void del(String key) {
        if (ObjectUtil.isEmpty(key)) {
            log.error("缓存键[{}]为空，无法删除缓存", key);
            return;
        }
        cacheService.delete(key);
    }

    /**
     *  采用val和Class的设计是基于灵活设置缓存key的，比如username，这时候会比传一个对象，获取类型和属性方便
     *  优先读取本地缓存，没有命中查询redis，并设置一分钟不再查询redis，减小redis请求压力
     *  当redis查询不为空或主动调用set方法设置缓存时，会更新本地缓存
     *  读写分离，避免并发缓存竞态条件
     */
    public <T extends BaseEntity> T getEntity(String val, Class<T> type) {
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

    public <T extends BaseEntity> void setEntity(String val, T entity, Duration... duration) {
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

    public <T extends BaseEntity> void delEntity(String val, Class<T> type) {
        if (ObjectUtil.isAllEmpty(val, type)) {
            log.error("缓存值[{}]或类型[{}]为空，无法删除缓存", val, type.getName());
            return;
        }
        AbstractCacheService<? extends BaseEntity> abstractCacheService = cacheServiceMap.get(type);
        if (abstractCacheService == null) {
            log.error("缓存服务[{}]未初始化，无法删除缓存值", type.getName());
            return;
        }
        AbstractCacheService<T> cacheService = CastUtils.cast(abstractCacheService);
        String key = cacheService.getCacheKey(val);
        cacheService.del(key);
    }

    public <T extends BaseEntity> Mono<Optional<T>> getReactive(String val, Class<T> type) {
        if (ObjectUtil.isAllEmpty(val, type)) {
            log.error("缓存值[{}]或类型[{}]为空，无法获取缓存", val, type.getName());
            return Mono.empty();
        }
        AbstractCacheService<? extends BaseEntity> abstractCacheService = cacheServiceMap.get(type);
        if (abstractCacheService == null) {
            log.error("缓存服务[{}]未初始化，无法获取缓存值", type.getName());
            return Mono.empty();
        }
        AbstractCacheService<T> cacheService = CastUtils.cast(abstractCacheService);
        String key = cacheService.getCacheKey(val);
        T local = cacheService.getLocal(key);
        if (local != null) {
            return Mono.just(Optional.of(local));
        }
        if (!cacheService.canQueryRedis(key)) {
            return Mono.empty();
        }
        return cacheService.getReactive(key, type);
    }


}