package com.boyu.entity.system;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.boyu.entity.BaseEntity;
import lombok.*;


@EqualsAndHashCode(callSuper = true)
@Data
@TableName("sys_user")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity extends BaseEntity {

    /**
     * 主键ID，自增
     */
    @TableId(type = IdType.AUTO) // 声明自增主键
    private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

     /**
      * 角色ID
      */
    private Long roleId;

}
