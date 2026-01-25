package com.hncu.controller;

import com.hncu.domain.SysRole;
import com.hncu.model.Result;
import com.hncu.service.SysRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author caimeisahng
 * @Date 2026/1/25 17:21
 * @Version 1.0
 * 系统角色管理控制层
 */

@Api("系统角色接口管理")
@RequestMapping("sys/role")
@RestController
public class SysRoleController {

    @Autowired
    private SysRoleService sysRoleService;
    @ApiOperation("查询系统所有角色")
    @GetMapping("list")
    @PreAuthorize("hasAuthority('sys:role:list')")
    public Result<List<SysRole>> loadSysRoleList(){
        List<SysRole> roleList = sysRoleService.querySysRoleList();

        return Result.success(roleList);
    }
}
