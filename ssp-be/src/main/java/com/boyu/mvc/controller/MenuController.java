package com.boyu.mvc.controller;

import com.boyu.service.system.MenuService;
import com.boyu.servlet.BsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/menu")
@RequiredArgsConstructor
public class MenuController {

    private final MenuService menuService;

    @RequestMapping("/list")
//    @PreAuthorize("hasAuthority('sys:menu:list')")
    public BsResponse list() {
        return BsResponse.ok(menuService.list());
    }

    @PostMapping("/save")
//    @PreAuthorize("hasAuthority('sys:menu:save')")
    public BsResponse save() {
        return BsResponse.ok();
    }

     @PostMapping("/delete")
//    @PreAuthorize("hasAuthority('sys:menu:delete')")
    public BsResponse delete() {
        return BsResponse.ok();
    }

     @PostMapping("/update")
//    @PreAuthorize("hasAuthority('sys:menu:update')")
    public BsResponse update() {
        return BsResponse.ok();
    }

}
