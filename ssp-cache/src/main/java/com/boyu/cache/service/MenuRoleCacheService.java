package com.boyu.cache.service;

import com.boyu.cache.enumerate.RedisPrefix;
import com.boyu.entity.system.MenuRoleEntity;
import org.springframework.stereotype.Service;

@Service
public class MenuRoleCacheService extends GenericCacheService<MenuRoleEntity> {

    public MenuRoleCacheService() {
        super(
                RedisPrefix.MENU_ROLE.getPrefix(), // 缓存前缀
                10, // 过期10分钟
                1000, // 最大容量1000
                MenuRoleEntity.class // 实体类型
        );
    }

}
