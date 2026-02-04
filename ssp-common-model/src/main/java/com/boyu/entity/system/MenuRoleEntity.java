package com.boyu.entity.system;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.boyu.entity.BaseEntity;
import lombok.*;

/**
 * 菜单角色实体类
 */
@EqualsAndHashCode(callSuper = true)
@TableName("sys_menu_role")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MenuRoleEntity extends BaseEntity {

    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 菜单ID
     */
    private Long menuId;

    /**
     * 角色ID
     */
    private Long roleId;
}
