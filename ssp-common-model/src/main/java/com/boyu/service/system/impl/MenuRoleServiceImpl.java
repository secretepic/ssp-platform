package com.boyu.service.system.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.boyu.entity.system.MenuRoleEntity;
import com.boyu.mapper.system.MenuRoleMapper;
import com.boyu.service.system.MenuRoleService;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MenuRoleServiceImpl extends ServiceImpl<MenuRoleMapper, MenuRoleEntity> implements MenuRoleService {
    
    @Override
    public List<Long> getMenuIdsByRoleId(Long roleId) {
        LambdaQueryWrapper<MenuRoleEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(MenuRoleEntity::getRoleId, roleId);
        List<MenuRoleEntity> menuRoleEntities = baseMapper.selectList(wrapper);
        return menuRoleEntities.stream().map(MenuRoleEntity::getMenuId).toList();
    }
    
    @Override
    @Transactional
    public boolean assignMenusToRole(Long roleId, List<Long> menuIds) {
        // 先删除该角色的所有菜单关联
        LambdaQueryWrapper<MenuRoleEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(MenuRoleEntity::getRoleId, roleId);
        baseMapper.delete(wrapper);
        
        // 再添加新的菜单关联
        if (menuIds != null && !menuIds.isEmpty()) {
            List<MenuRoleEntity> menuRoleEntities = menuIds.stream().map(menuId -> {
                MenuRoleEntity menuRoleEntity = new MenuRoleEntity();
                menuRoleEntity.setRoleId(roleId);
                menuRoleEntity.setMenuId(menuId);
                menuRoleEntity.setCreateTime(new Date());
                menuRoleEntity.setUpdateTime(new Date());
                return menuRoleEntity;
            }).toList();
            return saveBatch(menuRoleEntities);
        }
        return true;
    }
}