package com.boyu.service.system.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.boyu.entity.system.UserRoleEntity;
import com.boyu.mapper.system.UserRoleMapper;
import com.boyu.service.system.UserRoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRoleEntity> implements UserRoleService {
    
    @Override
    public List<Long> getRoleIdsByUserId(Long userId) {
        LambdaQueryWrapper<UserRoleEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserRoleEntity::getUserId, userId);
        List<UserRoleEntity> userRoleEntities = baseMapper.selectList(wrapper);
        return userRoleEntities.stream().map(UserRoleEntity::getRoleId).toList();
    }
    
    @Transactional
    @Override
    public boolean assignRolesToUser(Long userId, List<Long> roleIds) {
        // 获取用户已有的角色ID列表
        List<Long> existingRoleIds = getRoleIdsByUserId(userId);
        
        // 过滤出需要新增的角色ID（去重）
        List<Long> newRoleIds = roleIds.stream()
                .filter(roleId -> !existingRoleIds.contains(roleId))
                .toList();
        
        // 添加新的角色关联
        if (!newRoleIds.isEmpty()) {
            List<UserRoleEntity> userRoleEntities = newRoleIds.stream().map(roleId -> {
                UserRoleEntity userRoleEntity = new UserRoleEntity();
                userRoleEntity.setUserId(userId);
                userRoleEntity.setRoleId(roleId);
                userRoleEntity.setCreateTime(new Date());
                userRoleEntity.setUpdateTime(new Date());
                userRoleEntity.setStatus(true);
                userRoleEntity.setDelFlag(false);
                return userRoleEntity;
            }).toList();
            return saveBatch(userRoleEntities);
        }
        return true;
    }
    
    @Transactional
    @Override
    public boolean removeRolesFromUser(Long userId, List<Long> roleIds) {
        if (roleIds != null && !roleIds.isEmpty()) {
            LambdaQueryWrapper<UserRoleEntity> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(UserRoleEntity::getUserId, userId)
                   .in(UserRoleEntity::getRoleId, roleIds);
            return baseMapper.delete(wrapper) > 0;
        }
        return false;
    }
}