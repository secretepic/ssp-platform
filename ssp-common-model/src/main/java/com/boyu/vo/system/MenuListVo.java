package com.boyu.vo.system;

import lombok.Data;

import java.util.List;

@Data
public class MenuListVo {

    private Long id;

    /**
     * 父菜单ID
     */
    private Long parentId;

    /**
     * 菜单名称
     */
    private String menuName;

    /**
     * 图标
     */
    private String icon;

    /**
     * 类型 1-菜单 2-按钮
     */
    private Integer type;

    /**
     * 权限编码
     */
    private String permCode;

    /**
     * 排序
     */
    private Integer sort;

    private List<MenuListVo> children;
}
