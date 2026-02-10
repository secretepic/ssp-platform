package com.boyu.mvc.vo;

import com.boyu.entity.BaseEntity;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class LoginVo extends BaseEntity {

    @NotNull(message = "用户名不能为空")
    private String username;

    @NotNull(message = "密码不能为空")
    private String password;

    private Boolean fromApi = false;

    @NotNull(message = "验证码不能为空")
    private String captchaCode;

    @NotNull(message = "验证码ID不能为空")
    private String captchaId;

}
