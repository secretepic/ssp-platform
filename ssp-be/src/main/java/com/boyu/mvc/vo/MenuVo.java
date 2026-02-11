package com.boyu.mvc.vo;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MenuVo {

    private Long id;

    /**
     * 父菜单ID
     */
    @NotNull(message = "父菜单不能为空")
    private Long parentId;

    /**
     * 菜单名称
     */
    @NotNull(message = "菜单名称不能为空")
    private String menuName;

    /**
     * 图标
     */
    private String icon;

    /**
     * 类型 1-菜单 2-按钮
     */
    @NotNull(message = "类型不能为空")
    private Integer type;

    /**
     * 前端组件路径
     */
    private String frontPath;

    /**
     * 后端路由路径
     */
    private String backendRoute;

    /**
     * 权限编码
     */
    private String permCode;

    /**
     * 排序
     */
    private Integer sort;
}
