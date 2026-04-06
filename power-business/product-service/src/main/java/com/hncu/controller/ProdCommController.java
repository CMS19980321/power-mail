package com.hncu.controller;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hncu.domain.ProdComm;
import com.hncu.model.Result;
import com.hncu.service.ProdCommService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * @Author caimeisahng
 * @Date 2026/4/5 20:50
 * @Version 1.0
 * 商品评论管理控制层
 */

@Api(tags = "商品评论接口管理")
@RequestMapping("prod/prodComm")
@RestController
public class ProdCommController {
    @Autowired
    private ProdCommService prodCommService;

    /**
     * 多条件分页查询商品评论
     * @param current 当前页
     * @param size 每页显示条数
     * @param prodName 商品名称
     * @param status 评论状态
     * @return
     */
    @ApiOperation("多条件分页查询商品评论")
    @GetMapping("page")
    @PreAuthorize("hasAuthority('prod:prodComm:page')")
    public Result<Page<ProdComm>> loadProdCommPage(@RequestParam Long current,
                                                   @RequestParam Long size,
                                                   @RequestParam(required = false) String prodName,
                                                   @RequestParam(required = false) Integer status){

        Page<ProdComm> page = new Page<>(current,size);
        //多条件分页查询商品评论
        page = prodCommService.page(page,new LambdaQueryWrapper<ProdComm>()
                .eq(ObjectUtil.isNotNull(status),ProdComm::getStatus,status)
                .like(StringUtils.hasText(prodName),ProdComm::getProdName,prodName)
                .orderByDesc(ProdComm::getCreateTime)
        );
        return Result.success(page);
    }

    /**
     * 根据标识查询商品评论详情
     * @param id 商品评论详情标识
     * @return
     */
    @ApiOperation("根据标识查询商品评论详情")
    @GetMapping("{id}")
    @PreAuthorize("hasAuthority('prod:prodComm:page')")
    public Result<ProdComm> loadInfo(@PathVariable Long id){
        ProdComm prodComm = prodCommService.getById(id);
        return Result.success(prodComm);
    }

    @ApiOperation("回复与审核评论")
    @PutMapping("")
    @PreAuthorize("hasAuthority('prod:prodComm:update')")
    public Result<String> replayAndExamineProdComm(@RequestBody ProdComm prodComm){
        Boolean flag = prodCommService.replayAndExamineProdComm(prodComm);
        return Result.success(null);
    }
}
