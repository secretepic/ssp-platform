package com.boyu.service.system.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.boyu.entity.system.RoleEntity;
import com.boyu.entity.system.UserRoleEntity;
import com.boyu.entity.system.MenuRoleEntity;
import com.boyu.mapper.system.RoleMapper;
import com.boyu.mapper.system.UserRoleMapper;
import com.boyu.mapper.system.MenuRoleMapper;
import com.boyu.service.system.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.io.Serializable;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl extends ServiceImpl<RoleMapper, RoleEntity> implements RoleService {
    
    private final UserRoleMapper userRoleMapper;
    
    private final MenuRoleMapper menuRoleMapper;
    
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
    
    @Transactional
    @Override
    public boolean removeById(Serializable id) {
        // 删除角色与用户的关联
        LambdaQueryWrapper<UserRoleEntity> userRoleWrapper = new LambdaQueryWrapper<>();
        userRoleWrapper.eq(UserRoleEntity::getRoleId, id);
        userRoleMapper.delete(userRoleWrapper);
        
        // 删除角色与菜单的关联
        LambdaQueryWrapper<MenuRoleEntity> menuRoleWrapper = new LambdaQueryWrapper<>();
        menuRoleWrapper.eq(MenuRoleEntity::getRoleId, id);
        menuRoleMapper.delete(menuRoleWrapper);
        
        // 删除角色本身
        return super.removeById(id);
    }
}
