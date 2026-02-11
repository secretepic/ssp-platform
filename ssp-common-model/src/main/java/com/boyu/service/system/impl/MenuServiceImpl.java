package com.boyu.service.system.impl;

import cn.hutool.core.collection.ListUtil;
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
