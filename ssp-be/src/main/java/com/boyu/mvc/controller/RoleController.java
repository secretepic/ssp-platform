package com.boyu.mvc.controller;

import cn.hutool.core.bean.BeanUtil;
import com.boyu.entity.system.RoleEntity;
import com.boyu.mvc.vo.RoleVo;
import com.boyu.service.system.MenuRoleService;
import com.boyu.service.system.RoleService;
import com.boyu.servlet.BsResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/role")
@RequiredArgsConstructor
public class RoleController extends BaseController {

    private final RoleService roleService;
    private final MenuRoleService menuRoleService;

    @GetMapping("/list")
    public BsResponse list() {
        return BsResponse.ok(roleService.list());
    }

    @PostMapping("/add")
    public BsResponse add(@Valid @RequestBody RoleVo roleVo) {
        RoleEntity roleEntity = new RoleEntity();
        BeanUtil.copyProperties(roleVo, roleEntity);
        roleService.validateRole(roleEntity);
        setCreator(roleEntity);
        return roleService.save(roleEntity) ? BsResponse.ok() : BsResponse.error("添加失败");
    }

    @PutMapping("/update")
    public BsResponse update(@Valid @RequestBody RoleVo roleVo) {
        RoleEntity roleEntity = new RoleEntity();
        BeanUtil.copyProperties(roleVo, roleEntity);
        roleService.validateRole(roleEntity);
        setUpdater(roleEntity);
        return roleService.updateById(roleEntity) ? BsResponse.ok() : BsResponse.error("更新失败");
    }

    @DeleteMapping("/delete/{id}")
    public BsResponse delete(@PathVariable Long id) {
        return roleService.removeById(id) ? BsResponse.ok() : BsResponse.error("删除失败");
    }

    @GetMapping("/get/{id}")
    public BsResponse get(@PathVariable Long id) {
        RoleEntity roleEntity = roleService.getById(id);
        return roleEntity != null ? BsResponse.ok(roleEntity) : BsResponse.error("角色不存在");
    }

    @GetMapping("/menu/{roleId}")
    public BsResponse getRoleMenus(@PathVariable Long roleId) {
        List<Long> menuIds = menuRoleService.getMenuIdsByRoleId(roleId);
        return BsResponse.ok(menuIds);
    }

    @PostMapping("/menu/{roleId}")
    public BsResponse assignRoleMenus(@PathVariable Long roleId, @RequestBody List<Long> menuIds) {
        boolean success = menuRoleService.assignMenusToRole(roleId, menuIds);
        return success ? BsResponse.ok() : BsResponse.error("分配权限失败");
    }

}
