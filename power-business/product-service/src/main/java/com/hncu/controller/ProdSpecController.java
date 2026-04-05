package com.hncu.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hncu.domain.ProdProp;
import com.hncu.model.Result;
import com.hncu.service.ProdPropService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * @Author caimeisahng
 * @Date 2026/3/29 21:00
 * @Version 1.0
 * 商品规格管理控制层
 */

@Api(tags = "商品规格管理控制层")
@RestController
@RequestMapping("prod/spec")
public class ProdSpecController {
    @Autowired
    private ProdPropService prodPropService;

    /**
     *
     * @param current 当前页
     * @param size 每页显示条数
     * @param propName 属性名称
     * @return
     */

    @ApiOperation("多条件查询分页查询商品属性接口")
    @GetMapping("page")
    @PreAuthorize("hasAuthority('prod:spec:page')")
    public Result<Page<ProdProp>> loadProdSpecPage(@RequestParam Long current,
                                                   @RequestParam Long size,
                                                   @RequestParam(required = false) String propName){
        //多条件分页查询商品规格
        Page<ProdProp> page = prodPropService.queryProdSpecPage(current, size, propName);

        return Result.success(page);
    }

    /**
     * 新增商品规格
     * @param prodProp 商品属性对象
     * @return
     */
    @ApiOperation("新增商品规格")
    @PostMapping("")
    @PreAuthorize("hasAuthority('prod:spec:save')")
    public Result<String> saveProdSpec(@RequestBody ProdProp prodProp){
        Boolean saved = prodPropService.saveProdSpec(prodProp);
        return Result.handle(saved);
    }


    /**
     * 修改商品规格信息
     * @return prodProp 商品属性对象
     */
    @ApiOperation("修改商品规格")
    @PutMapping("")
    @PreAuthorize("hasAuthority('prod:spec:update')")
    public Result<String> modifyProdSpec(@RequestBody ProdProp prodProp){
        Boolean modified = prodPropService.modifyProdSpec(prodProp);
        return Result.handle(modified);
    }

    /**
     * 删除商品规格
     * @param prodId 属性标识
     * @return
     */
    @ApiOperation("删除商品规格")
    @DeleteMapping("{prodId}")
    @PreAuthorize("hasAuthority('prod:spec:delete')")
    public Result<String> removeProdSpec(@PathVariable Long prodId){
        Boolean removed = prodPropService.removeProdSpecByPropId(prodId);
        return Result.handle(removed);
    }
}
