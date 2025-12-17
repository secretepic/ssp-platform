package com.boyu.mvc.vo;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LoginVo {

    @NotNull(message = "用户名不能为空")
    private String username;

    @NotNull(message = "密码不能为空")
    private String password;

    private Integer status;

    private Boolean fromApi = false;

}
