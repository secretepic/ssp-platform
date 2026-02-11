package com.boyu.service.system.impl;

import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.boyu.entity.system.MenuEntity;
import com.boyu.mapper.system.MenuMapper;
import com.boyu.service.system.MenuService;
import com.boyu.vo.system.MenuTreeVo;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, MenuEntity> implements MenuService {
    @Override
    public MenuTreeVo tree() {
        List<MenuEntity> meneList = list(new QueryWrapper<MenuEntity>().orderByAsc("id"));
        if (meneList.isEmpty()) {
            return null;
        }
        return buildTree(meneList);
    }

    @Override
    public void validateMenu(MenuEntity menuEntity) {
        Integer type = menuEntity.getType();
        if (type == 1) {
            if (StrUtil.isBlank(menuEntity.getFrontPath())) {
                throw new IllegalArgumentException("菜单类型为菜单时，前端组件路径不能为空");
            }
        } else {
            if (StrUtil.isBlank(menuEntity.getPermCode())) {
                throw new IllegalArgumentException("菜单类型为按钮时，权限编码不能为空");
            }
        }
    }

    private MenuTreeVo buildTree(List<MenuEntity> menuList) {
        MenuTreeVo menuTreeVo = new MenuTreeVo();
        MenuEntity root = menuList.get(0);
        menuTreeVo.setId(root.getId());
        menuTreeVo.setName(root.getMenuName());
        Map<Long, List<MenuEntity>> parentIdToChildrenMap = menuList.stream()
                .collect(Collectors.groupingBy(MenuEntity::getParentId));
        Queue<MenuTreeVo> queue = new LinkedList<>();
        queue.add(menuTreeVo);
        while (!queue.isEmpty()) {
            MenuTreeVo current = queue.poll();
            Long currentId = current.getId();
            List<MenuEntity> children = parentIdToChildrenMap.getOrDefault(currentId, ListUtil.empty());
            List<MenuTreeVo> childTreeVos = children.stream()
                    .map(menu -> {
                        MenuTreeVo childTreeVo = new MenuTreeVo();
                        childTreeVo.setId(menu.getId());
                        childTreeVo.setName(menu.getMenuName());
                        return childTreeVo;
                    })
                    .sorted(Comparator.comparing(MenuTreeVo::getId))
                    .collect(Collectors.toList());
            current.setChildren(childTreeVos);
            queue.addAll(childTreeVos);
        }
        return menuTreeVo;
    }
}
