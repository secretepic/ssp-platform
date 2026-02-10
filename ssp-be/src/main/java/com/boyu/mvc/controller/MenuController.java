package com.boyu.mvc.controller;

import com.boyu.service.system.MenuService;
import com.boyu.servlet.BsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/menu")
@RequiredArgsConstructor
public class MenuController {

    private final MenuService menuService;

    @RequestMapping("/list")
    public BsResponse list() {
        return BsResponse.ok(menuService.list());
    }

}
