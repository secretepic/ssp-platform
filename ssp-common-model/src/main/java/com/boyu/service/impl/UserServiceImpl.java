
package com.boyu.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.boyu.entity.system.UserEntity;
import com.boyu.mapper.UserMapper;
import com.boyu.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, UserEntity> implements UserService {

}
