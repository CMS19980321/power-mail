package com.hncu.controller;

import com.hncu.domain.Category;
import com.hncu.model.Result;
import com.hncu.service.CategoryService;
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
 * @Date 2026/3/21 20:32
 * @Version 1.0
 * 商品类目控制类
 */

@Api(tags = "商品类目接口管理")
@RequestMapping("prod/category")
@RestController
public class CategoryController {

    @Autowired
    private CategoryService categoryService;



    @ApiOperation("查询系统所有的商品类目")
    @GetMapping("table")
    @PreAuthorize("hasAuthority('prod:category:page')")
    public Result<List<Category>> loadAllCategoryList(){
        List<Category> list = categoryService.queryAllCategoryList();
        return Result.success(list);
    }

    @ApiOperation("查询系统商品一级类目")
    @GetMapping("listCategory")
    @PreAuthorize("hasAuthority('prod:category:page')")
    public Result<List<Category>> loadFirstCategory(){
        List<Category> list = categoryService.queryFirstCategoryList();
        return Result.success(list);
    }

}
