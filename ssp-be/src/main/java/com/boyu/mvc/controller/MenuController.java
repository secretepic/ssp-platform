package com.boyu.mvc.controller;

import com.boyu.vo.system.MenuVo;
import com.boyu.service.system.MenuService;
import com.boyu.servlet.BsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/menu")
@RequiredArgsConstructor
public class MenuController {

    private final MenuService menuService;

    @GetMapping("/list")
//    @PreAuthorize("hasAuthority('sys:menu:list')")
    public BsResponse list(MenuVo menuVo) {
        return BsResponse.ok(menuService.list());
    }

    @GetMapping("/tree")
    //    @PreAuthorize("hasAuthority('sys:menu:tree')")
    public BsResponse tree() {
        return BsResponse.ok(menuService.tree());
    }

    @PostMapping("/save")
//    @PreAuthorize("hasAuthority('sys:menu:save')")
    public BsResponse save(@RequestBody MenuVo menuVo) {
        return BsResponse.ok();
    }

     @PostMapping("/delete/{id}")
//    @PreAuthorize("hasAuthority('sys:menu:delete')")
    public BsResponse delete(@PathVariable Long id) {
        return BsResponse.ok();
    }

     @PostMapping("/update")
//    @PreAuthorize("hasAuthority('sys:menu:update')")
    public BsResponse update(@RequestBody MenuVo menuVo) {
        return BsResponse.ok();
    }

}
