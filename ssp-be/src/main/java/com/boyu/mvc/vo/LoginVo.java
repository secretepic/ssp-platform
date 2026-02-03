package com.boyu.mvc.vo;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LoginVo {

    @NotNull(message = "用户名不能为空")
    private String username;

    @NotNull(message = "密码不能为空")
    private String password;

    private Boolean status;

    private Boolean fromApi = false;

    @NotNull(message = "验证码不能为空")
    private String captchaCode;

    @NotNull(message = "验证码ID不能为空")
    private String captchaId;

}
