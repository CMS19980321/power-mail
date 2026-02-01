package com.hncu.controller;

import com.hncu.domain.SysMenu;
import com.hncu.model.Result;
import com.hncu.model.SecurityUser;
import com.hncu.service.SysMenuService;
import com.hncu.util.AuthUtils;
import com.hncu.vo.MenuAndAuth;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

/**
 * @Author caimeisahng
 * @Date 2025/11/20 20:49
 * @Version 1.0
 * 系统权限管理层
 */

@Api(tags = "系统权限接口管理")
@RequestMapping("sys/menu")
@RestController
public class SysMenuController {
    @Autowired
    private SysMenuService sysMenuService;

    /**
     * 查询用户的菜单权限和操作(按钮)权限
     * @return
     */
    @GetMapping("nav")
    @ApiOperation("查询用户的菜单权限和操作(按钮)权限")
    public Result<MenuAndAuth> loadMenuAndAuth(){
        //获取当前的用户标识(userId)
        Long loginUserId = AuthUtils.getLoginUserId();
        //更具用户标识获取操作权限集合
        Set<String> perms = AuthUtils.getLoginUserPerms();

        //根据用户标识获取菜单权限集合
        Set<SysMenu> menus = sysMenuService.queryUserMenuListByUserId(loginUserId);

        MenuAndAuth menuAndAuth = new MenuAndAuth(menus,perms);


        return Result.success(menuAndAuth);
    }

    /**
     * 查询系统所有权限集合
     * @return
     */
    @ApiOperation("查询系统所有权限集合")
    @GetMapping("table")
    @PreAuthorize("hasAuthority('sys:menu:list')")
    public Result<List<SysMenu>> loadAllSysMenuList(){
        List<SysMenu> list = sysMenuService.queryAllSysMenuList();
        return Result.success(list);
    }

    /**
     * 新增权限
     * @param sysMenu 系统权限对象
     * @return
     */
    @ApiOperation("新增权限")
    @PostMapping
    @PreAuthorize("hasAuthority('sys:menu:save')")
    public Result<String> saveSysMenu(@RequestBody SysMenu sysMenu){
        Boolean saved = sysMenuService.saveSysMenu(sysMenu);
        return Result.handle(saved);
    }

    /**
     * 根据标识查询菜单权限信息
     * @param menuId 菜单权限标识
     * @return
     */
    @ApiOperation("根据标识查询菜单权限信息")
    @GetMapping("info/{menuId}")
    @PreAuthorize("hasAuthority('sys:menu:info')")
    public Result<SysMenu> loadSysMenuInfo(@PathVariable Long menuId){
        SysMenu sysMenu = sysMenuService.getById(menuId);
        return Result.success(sysMenu);
    }

    /**
     *
     * @param sysMenu 系统权限对象
     * @return
     */
    @ApiOperation("修改菜单权限信息")
    @PutMapping("")
    @PreAuthorize("hasAuthority('sys:menu:update')")
    public Result<String> modifySysMenu(@RequestBody SysMenu sysMenu){
        Boolean modified = sysMenuService.modifySysMenu(sysMenu);
        return Result.handle(modified);
    }

    @ApiOperation("删除菜单权限")
    @DeleteMapping("{menuId}")
    @PreAuthorize("hasAuthority('sys:menu:delete')")
    public Result<String> removeSysMenu(@PathVariable Long menuId){
        Boolean removed = sysMenuService.removeSysMenuById(menuId);
        return Result.handle(removed);
    }
}
