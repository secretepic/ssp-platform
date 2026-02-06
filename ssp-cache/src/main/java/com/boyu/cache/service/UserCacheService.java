package com.boyu.cache.service;

import com.boyu.cache.enumerate.RedisPrefix;
import com.boyu.entity.system.UserEntity;
import org.springframework.stereotype.Service;

@Service
public class UserCacheService extends GenericCacheService<UserEntity> {

    public UserCacheService() {
        super(
                RedisPrefix.USER_INFO.getPrefix(), // 缓存前缀
                10, // 过期10分钟
                1000, // 最大容量1000
                UserEntity.class // 实体类型
        );
    }
}
