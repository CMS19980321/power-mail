package com.hncu.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hncu.domain.ProdProp;
import com.hncu.model.Result;
import com.hncu.service.ProdPropService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author caimeisahng
 * @Date 2026/3/29 21:00
 * @Version 1.0
 * 商品规格管理控制层
 */

@Api(tags = "商品规格管理控制层")
@RestController
@RequestMapping("prod/spec/page")
public class ProdSpecController {
    @Autowired
    private ProdPropService prodPropService;

    @ApiOperation("多条件查询分页查询接口")
    @GetMapping("page")
    @PreAuthorize("hasAuthority('prod:spec:page')")
    public Result<Page<ProdProp>> loadProdSpecPage(@RequestParam Long current,
                                                   @RequestParam Long size,
                                                   @RequestParam(required = false) String propName){
        //多条件分页查询商品规格
        Page<ProdProp> page = prodPropService.queryProdSpecPage(current, size, propName);

        return Result.success(page);
    }
}
