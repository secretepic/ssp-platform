package com.boyu.entity;

import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;

import java.util.Date;

@Data
public class BaseEntity {

    /**
     * 创建人ID
     */
    private Long creator;

    /**
     * 创建时间
     */
    private Long updater;

    /**
     * 更新人ID
     */
    private Date createTime;

    /**
     * 更新时间（默认当前时间戳）
     */
    private Date updateTime;

    /**
     * 删除标志（0-正常，1-已删除）
     */
    @TableLogic
    private Boolean delFlag;
}
