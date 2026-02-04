package com.boyu.entity.system;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.boyu.entity.BaseEntity;
import lombok.*;

/**
 * 菜单实体类
 */
@EqualsAndHashCode(callSuper = true)
@TableName("sys_menu")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MenuEntity extends BaseEntity {

    @TableId(type = IdType.AUTO)
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
      * 类型 1-菜单 2-按钮
      */
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
