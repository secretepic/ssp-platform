package com.boyu.service.system.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.boyu.entity.system.RoleEntity;
import com.boyu.mapper.system.RoleMapper;
import com.boyu.service.system.RoleService;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, RoleEntity> implements RoleService {
    
    @Override
    public void validateRole(RoleEntity roleEntity) {
        // 验证角色名称唯一性
        LambdaQueryWrapper<RoleEntity> nameWrapper = new LambdaQueryWrapper<>();
        nameWrapper.eq(RoleEntity::getRoleName, roleEntity.getRoleName());
        if (roleEntity.getId() != null) {
            nameWrapper.ne(RoleEntity::getId, roleEntity.getId());
        }
        if (baseMapper.selectCount(nameWrapper) > 0) {
            throw new IllegalArgumentException("角色名称已存在");
        }
        
        // 验证角色编码唯一性
        LambdaQueryWrapper<RoleEntity> codeWrapper = new LambdaQueryWrapper<>();
        codeWrapper.eq(RoleEntity::getRoleCode, roleEntity.getRoleCode());
        if (roleEntity.getId() != null) {
            codeWrapper.ne(RoleEntity::getId, roleEntity.getId());
        }
        if (baseMapper.selectCount(codeWrapper) > 0) {
            throw new IllegalArgumentException("角色编码已存在");
        }
    }
}
