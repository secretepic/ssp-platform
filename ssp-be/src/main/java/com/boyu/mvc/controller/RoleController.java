package com.boyu.mvc.controller;

import com.boyu.service.system.RoleService;
import com.boyu.servlet.BsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/role")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @RequestMapping("/list")
    public BsResponse list() {
        return BsResponse.ok(roleService.list());
    }

}
