package com.boyu.mvc.controller;

import cn.hutool.core.bean.BeanUtil;
import com.boyu.entity.system.MenuEntity;
import com.boyu.mvc.vo.MenuVo;
import com.boyu.service.system.MenuService;
import com.boyu.servlet.BsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/menu")
@RequiredArgsConstructor
public class MenuController extends BaseController {

    private final MenuService menuService;

    @GetMapping("/list")
//    @PreAuthorize("hasAuthority('sys:menu:list')")
    public BsResponse list(MenuVo menuVo) {
        return BsResponse.ok(menuService.list());
    }

    @GetMapping("/tree")
    //    @PreAuthorize("hasAuthority('sys:menu:tree')")
    public BsResponse tree() {
        // 这里需要加一个参数，根据id查询菜单树
        return BsResponse.ok(menuService.tree());
    }

    @PostMapping("/save")
//    @PreAuthorize("hasAuthority('sys:menu:save')")
    public BsResponse save(@RequestBody MenuVo menuVo) {
        MenuEntity menuEntity = new MenuEntity();
        BeanUtil.copyProperties(menuVo, menuEntity);
        menuService.validateMenu(menuEntity);
        setCreator(menuEntity);
        return menuService.save(menuEntity) ? BsResponse.ok() : BsResponse.error("菜单保存失败");
    }

     @PostMapping("/delete/{id}")
//    @PreAuthorize("hasAuthority('sys:menu:delete')")
    public BsResponse delete(@PathVariable Long id) {
        return menuService.removeById(id) ? BsResponse.ok() : BsResponse.error("菜单删除失败");
    }

     @PostMapping("/update")
//    @PreAuthorize("hasAuthority('sys:menu:update')")
    public BsResponse update(@RequestBody MenuVo menuVo) {
        MenuEntity menuEntity = new MenuEntity();
        BeanUtil.copyProperties(menuVo, menuEntity);
        menuService.validateMenu(menuEntity);
        setUpdater(menuEntity);
        return menuService.updateById(menuEntity) ? BsResponse.ok() : BsResponse.error("菜单更新失败");
    }

}
