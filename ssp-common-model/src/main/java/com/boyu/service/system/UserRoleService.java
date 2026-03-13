package com.boyu.service.system;

import com.baomidou.mybatisplus.extension.service.IService;
import com.boyu.entity.system.UserRoleEntity;

import java.util.List;

public interface UserRoleService extends IService<UserRoleEntity> {
    
    /**
     * 根据用户ID获取角色ID列表
     * @param userId 用户ID
     * @return 角色ID列表
     */
    List<Long> getRoleIdsByUserId(Long userId);
    
    /**
     * 为用户授予角色
     * @param userId 用户ID
     * @param roleIds 角色ID列表
     * @return 是否成功
     */
    boolean assignRolesToUser(Long userId, List<Long> roleIds);
    
    /**
     * 从用户移除角色
     * @param userId 用户ID
     * @param roleIds 角色ID列表
     * @return 是否成功
     */
    boolean removeRolesFromUser(Long userId, List<Long> roleIds);
}