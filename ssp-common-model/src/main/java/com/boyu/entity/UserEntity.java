package com.boyu.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;



@EqualsAndHashCode(callSuper = true)
@Data
@TableName("sys_user")
public class UserEntity extends BaseEntity {
    /**
     * 主键ID，自增
     */
    @TableId(type = IdType.AUTO) // 声明自增主键
    private Integer id;
    /**
     * 用户名
     */
    private String userName;

    /**
     * 密码
     */
    private String password;

    /**
     * 状态（例如：0-禁用，1-正常）
     */
    private Integer status;

     /**
      * 角色ID
      */
    private Long roleId;

     /**
      * 删除标志（0-正常，1-已删除）
      */
    @TableLogic
    private Boolean delFlag;

}
