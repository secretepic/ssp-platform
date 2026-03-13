package com.boyu.service.system;

import com.baomidou.mybatisplus.extension.service.IService;
import com.boyu.entity.system.RoleEntity;

public interface RoleService extends IService<RoleEntity> {
    
    /**
     * 验证角色信息
     * @param roleEntity 角色实体
     */
    void validateRole(RoleEntity roleEntity);
}
