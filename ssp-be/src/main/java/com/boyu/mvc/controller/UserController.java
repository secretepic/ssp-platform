package com.boyu.mvc.controller;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import cn.hutool.core.lang.UUID;
import com.boyu.cache.CacheService;
import com.boyu.entity.UserEntity;
import com.boyu.service.UserService;
import com.boyu.mvc.vo.LoginVo;
import com.boyu.servlet.BsResponse;
import com.boyu.util.DecryptionUtil;
import com.boyu.util.JwtUtil;
import com.boyu.util.SecurityUtil;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.Duration;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final CacheService cacheService;

    private final UserService userService;

    private final JwtUtil jwtUtil;

    private final AuthenticationManager authenticationManager;


    private final PasswordEncoder passwordEncoder;

    private static final String JWT_REDIS_PREFIX = "jwt:token:";


    @RequestMapping("/list")
    public List<UserEntity> list() {
        return userService.list();
    }

    /**
     * 登录核心流程
     * Authentication authentication = authenticationManager.authenticate(upToken);
     * 这个地方会调用DaoAuthenticationProvider，就是securityConfig里配置的
     * 先调用loadUserByUsername方法，会得到一个UserDetails
     * 紧接着会调用DaoAuthenticationProvider的additionalAuthenticationChecks方法
     * 这个方法里有一行if (!this.passwordEncoder.matches(presentedPassword, userDetails.getPassword()))
     * presentedPassword这个是来源于 new UsernamePasswordAuthenticationToken(username, password);
     * 这里会根据BCryptPasswordEncoder这个对象来加密vo里的密码，和数据库的对比，如果一致则返回true
     */
    @PostMapping("/login")
    public BsResponse login(@Valid @RequestBody LoginVo loginVo) {
        if (verifyCaptcha(loginVo.getCaptchaId(), loginVo.getCaptchaCode())) {
            return BsResponse.error("验证码错误");
        }
        String username = loginVo.getUsername();
        String password = loginVo.getFromApi() ? loginVo.getPassword() : DecryptionUtil.decrypt(loginVo.getPassword());
        UsernamePasswordAuthenticationToken upToken = new UsernamePasswordAuthenticationToken(username, password);
        authenticationManager.authenticate(upToken);
        String token = jwtUtil.generateToken(username);
        cacheService.set(JWT_REDIS_PREFIX + username, token, Duration.ofSeconds(1800));
        BsResponse res = BsResponse.ok();
        res.put("token", token);
        return res;
    }

    @GetMapping("/logout")
    public BsResponse logout() {
        SecurityUtil.getCurrentUser().ifPresent(user -> cacheService.delete(JWT_REDIS_PREFIX + user.getUsername()));
        return BsResponse.ok();
    }

    @PostMapping("/register")
    public BsResponse register(@RequestBody LoginVo loginVo) {
        if (verifyCaptcha(loginVo.getCaptchaId(), loginVo.getCaptchaCode())) {
            return BsResponse.error("验证码错误");
        }
        UserEntity userEntity = new UserEntity();
        userEntity.setUserName(loginVo.getUsername());
        userEntity.setPassword(passwordEncoder.encode(loginVo.getPassword()));
        userEntity.setStatus(loginVo.getStatus());
        SecurityUtil.getCurrentUser().ifPresent(user -> userEntity.setCreator(user.getId()));
        userEntity.setCreateTime(new Date());
        userEntity.setUpdateTime(new Date());
        return userService.save(userEntity) ? BsResponse.ok() : BsResponse.error("注册失败");
    }

    @GetMapping("/captcha")
    public void captcha(HttpServletResponse response) throws IOException {
        LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(200, 100);
        lineCaptcha.write(response.getOutputStream());
        String uuid = UUID.fastUUID().toString();
        String code = lineCaptcha.getCode();
        response.setHeader("captcha-id", uuid);
        cacheService.set(uuid, code, Duration.ofSeconds(60));
    }

    private boolean verifyCaptcha(String captchaId, String captchaCode) {
        return cacheService.get(captchaId, String.class).map(code -> !code.equalsIgnoreCase(captchaCode)).orElse(true);
    }
}
