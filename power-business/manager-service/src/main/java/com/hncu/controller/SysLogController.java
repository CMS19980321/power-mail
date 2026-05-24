package com.hncu.controller;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hncu.domain.SysLog;
import com.hncu.model.Result;
import com.hncu.service.SysLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author caimeisahng
 * @Date 2026/2/3 3:35
 * @Version 1.0
 * 系统操作日志管理控制层
 */

@Api("系统操作日志接口管理")
@RequestMapping("sys/log")
@RestController
public class SysLogController {

    @Autowired
    private SysLogService sysLogService;

    /**
     * 多条件分页查询系统操作日志
     * @param current 页码
     * @param size 每页显示条数
     * @param userId 用户标识
     * @param operation 用户操作描述
     * @return
     */
    @ApiOperation("多条件分页查询系统操作日志")
    @GetMapping("page")
    @PreAuthorize("hasAuthority('sys:log:page')")
    public Result<Page<SysLog>> loadSysLogPage(@RequestParam Long current,
                                               @RequestParam Long size,
                                               @RequestParam(required = false) Long userId,
                                               @RequestParam(required = false) String operation){

        //创建分页对象
        Page<SysLog> page = new Page<>(current,size);
        //多条件分页查询系统的操作日志
        page = sysLogService.page(page,new LambdaQueryWrapper<SysLog>()
                .eq(ObjectUtil.isNotEmpty(userId),SysLog::getUserId,userId)
                .like(StringUtils.hasText(operation),SysLog::getOperation,operation));
        return Result.success(page);
    }
}
