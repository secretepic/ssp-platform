package com.boyu.mvc.controller;

import com.boyu.entity.BaseEntity;
import com.boyu.util.SecurityUtil;

public class BaseController {

    public <T extends BaseEntity> void setCreator(T entity) {
        SecurityUtil.getCurrentUser().ifPresent(userPrincipal -> entity.setCreator(userPrincipal.getId()));
    }

    public <T extends BaseEntity> void setUpdater(T entity) {
        SecurityUtil.getCurrentUser().ifPresent(userPrincipal -> entity.setUpdater(userPrincipal.getId()));
    }
}
