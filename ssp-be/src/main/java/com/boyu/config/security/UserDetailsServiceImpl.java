package com.boyu.config.security;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.boyu.cache.CacheService;
import com.boyu.entity.system.UserEntity;
import com.boyu.mapper.system.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserMapper userMapper;
    private final CacheService cacheService;
    private static final String REDIS_USER_PREFIX = "user:info:";
    private static final long USER_CACHE_EXPIRATION = 30 * 60;


    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 先从Redis查询用户
        String userStr = cacheService.get(REDIS_USER_PREFIX + username, String.class).orElse(null);
        UserEntity user = JSONObject.parseObject(userStr, UserEntity.class);
        if (user == null || user.getId() == null) {
            QueryWrapper<UserEntity> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("user_name", username);
            user = userMapper.selectOne(queryWrapper);
            Objects.requireNonNull(user, "用户不存在");
            // 查询到的用户信息存入Redis
            cacheService.set(
                    REDIS_USER_PREFIX + username,
                    JSONObject.toJSONString(user),
                    Duration.ofSeconds(USER_CACHE_EXPIRATION)
            );
        }
        return UserPrincipal.build(user);
    }
}
