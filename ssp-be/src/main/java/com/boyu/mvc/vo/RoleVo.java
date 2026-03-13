package com.boyu.mvc.vo;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RoleVo {

    private Long id;

    /**
     * 角色名称
     */
    @NotNull(message = "角色名称不能为空")
    private String roleName;

    /**
     * 角色编码
     */
    @NotNull(message = "角色编码不能为空")
    private String roleCode;

    /**
     * 状态
     */
    private Boolean status;
}
