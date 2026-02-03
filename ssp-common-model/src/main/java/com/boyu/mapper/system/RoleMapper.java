package com.boyu.mapper.system;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.boyu.entity.system.RoleEntity;

import java.util.List;

public interface RoleMapper extends BaseMapper<RoleEntity> {
    /**
     * 根据角色ID查询权限
     *
     * @param roleId 角色ID
     * @return 权限列表
     */
    List<String> selectPermsByRoleId(Long roleId);
}
