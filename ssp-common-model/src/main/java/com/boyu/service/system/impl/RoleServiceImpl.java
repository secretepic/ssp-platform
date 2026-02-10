package com.boyu.service.system.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.boyu.entity.system.RoleEntity;
import com.boyu.mapper.system.RoleMapper;
import com.boyu.service.system.RoleService;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, RoleEntity> implements RoleService {
}
