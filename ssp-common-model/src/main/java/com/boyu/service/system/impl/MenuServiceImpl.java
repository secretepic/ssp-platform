package com.boyu.service.system.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.boyu.entity.system.MenuEntity;
import com.boyu.mapper.system.MenuMapper;
import com.boyu.service.system.MenuService;
import com.boyu.vo.system.MenuListVo;
import com.boyu.vo.system.MenuTreeVo;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, MenuEntity> implements MenuService {
    @Override
    public MenuTreeVo tree() {
        List<MenuEntity> menuList = list(new QueryWrapper<MenuEntity>().orderByAsc("id"));
        if (menuList.isEmpty()) {
            return null;
        }
        return buildTree(menuList);
    }

    @Override
    public void validateMenu(MenuEntity menuEntity) {
        Integer type = menuEntity.getType();
        if (type == 1 && !menuEntity.getParentId().equals(1L)) {
            if (StrUtil.isBlank(menuEntity.getFrontPath())) {
                throw new IllegalArgumentException("菜单类型为菜单时，前端组件路径不能为空");
            }
        } else {
            if (StrUtil.isBlank(menuEntity.getPermCode())) {
                throw new IllegalArgumentException("菜单类型为按钮时，权限编码不能为空");
            }
        }
    }

    @Override
    public List<MenuListVo> list(String menuName) {
        List<MenuEntity> menuList = list(new QueryWrapper<MenuEntity>().orderByAsc("id"));
        if (menuList.isEmpty()) {
            return null;
        }
        menuList.remove(0);
        List<MenuEntity> contents = menuList.stream().filter(menuEntity -> menuEntity.getParentId().equals(1L)).toList();
        List<MenuListVo> menuListVos = contents.stream().map(menuEntity -> {
            MenuListVo menuListVo = new MenuListVo();
            BeanUtil.copyProperties(menuEntity, menuListVo);
            return menuListVo;
        }).toList();
        Map<Long, List<MenuEntity>> parentIdToChildrenMap = menuList.stream()
                .collect(Collectors.groupingBy(MenuEntity::getParentId));
        Queue<MenuListVo> queue = new LinkedList<>(menuListVos);
        while (!queue.isEmpty()) {
            MenuListVo current = queue.poll();
            Long currentId = current.getId();
            List<MenuEntity> children = parentIdToChildrenMap.getOrDefault(currentId, ListUtil.empty());
            List<MenuListVo> childTreeVos = children.stream()
                    .map(menu -> {
                        MenuListVo childTreeVo = new MenuListVo();
                        BeanUtil.copyProperties(menu, childTreeVo);
                        return childTreeVo;
                    })
                    .sorted(Comparator.comparing(MenuListVo::getId))
                    .collect(Collectors.toList());
            current.setChildren(childTreeVos);
            queue.addAll(childTreeVos);
        }
        if (StrUtil.isNotBlank(menuName)) {
            for (MenuListVo menuListVo : menuListVos) {
                // 这里因为上面的循环结束,所以现在一定是空的
                queue.add(menuListVo);
                while (!queue.isEmpty()) {
                    MenuListVo poll = queue.poll();
                    if (poll.getMenuName().equals(menuName)) {
                        return ListUtil.list(false, menuListVo);
                    }
                    if (CollectionUtil.isNotEmpty(poll.getChildren())) {
                        queue.addAll(poll.getChildren());
                    }
                }
            }
        }
        return menuListVos;
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
