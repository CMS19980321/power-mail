package com.hncu.controller;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hncu.domain.Prod;
import com.hncu.model.Result;
import com.hncu.service.ProdService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * @Author caimeisahng
 * @Date 2026/4/6 20:56
 * @Version 1.0
 * 商品管理控制层
 */

@Api(tags = "商品接口管理")
@RequestMapping("prod/prod")
@RestController
public class ProdController {

    @Autowired
    private ProdService prodService;

    /**
     *
     * @param current 当前页
     * @param size 每页显示条数
     * @param prodName 商品名称
     * @param status 商品状态
     * @return
     */
    @ApiOperation("多条件分页查询商品")
    @GetMapping("page")
    @PreAuthorize("hasAuthority('prod:prod:page')")
    public Result<Page<Prod>> loadProdPage(@RequestParam Long current,
                                           @RequestParam Long size,
                                           @RequestParam(required = false) String prodName,
                                           @RequestParam(required = false) Long status){
        //创建分页对象
        Page<Prod> page = new Page<>(current, size);
        page = prodService.page(page,new LambdaQueryWrapper<Prod>()
                .eq(ObjectUtil.isNotNull(status),Prod::getStatus,status)
                .like(StringUtils.hasText(prodName),Prod::getProdName,prodName)
                .orderByDesc(Prod::getCreateTime)
        );


        return Result.success(page);
    }

    @ApiOperation("新增商品")
    @PostMapping("")
    @PreAuthorize("hasAuthority('prod:prod:save')")
    public Result<String> saveProd(@RequestBody Prod prod){
        Boolean saved = prodService.saveProd(prod);
        return Result.handle(saved);
    }
}
