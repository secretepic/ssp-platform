package com.boyu.service.system;

import com.baomidou.mybatisplus.extension.service.IService;
import com.boyu.entity.system.MenuEntity;
import com.boyu.vo.system.MenuTreeVo;

public interface MenuService extends IService<MenuEntity> {

    MenuTreeVo tree();

    void validateMenu(MenuEntity menuEntity);
}
