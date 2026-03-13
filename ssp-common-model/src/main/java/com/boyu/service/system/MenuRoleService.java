package com.boyu.service.system;

import com.baomidou.mybatisplus.extension.service.IService;
import com.boyu.entity.system.MenuRoleEntity;

import java.util.List;

public interface MenuRoleService extends IService<MenuRoleEntity> {
    
    /**
     * 根据角色ID获取菜单ID列表
     * @param roleId 角色ID
     * @return 菜单ID列表
     */
    List<Long> getMenuIdsByRoleId(Long roleId);
    
    /**
     * 为角色分配菜单权限
     * @param roleId 角色ID
     * @param menuIds 菜单ID列表
     * @return 是否成功
     */
    boolean assignMenusToRole(Long roleId, List<Long> menuIds);
}