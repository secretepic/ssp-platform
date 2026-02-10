package com.boyu.service.system.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.boyu.entity.system.MenuEntity;
import com.boyu.mapper.system.MenuMapper;
import com.boyu.service.system.MenuService;
import org.springframework.stereotype.Service;

@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, MenuEntity> implements MenuService {
}
