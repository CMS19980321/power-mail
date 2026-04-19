package com.hncu.controller;

import com.hncu.domain.Area;
import com.hncu.model.Result;
import com.hncu.service.AreaService;
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
 * @Date 2026/4/19 20:36
 * @Version 1.0
 * 地区业务模块控制层
 */

@RestController
@RequestMapping("admin/area")
@Api(tags = "地区业务接口管理")
public class AreaController {
    @Autowired
    private AreaService areaService;

    /**
     * 查询全国地区列表接口
     * @return
     */
    @ApiOperation("查询全国地区列表")
    @GetMapping("list")
    @PreAuthorize("hasAuthority('admin:area:list')")
    public Result<List<Area>> loadAllAreaList(){
        List<Area> areas = areaService.queryAllAreaList();
        return Result.success(areas);
    }
}
