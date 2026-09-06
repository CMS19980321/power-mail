package com.hncu.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hncu.domain.Category;
import com.hncu.model.Result;
import com.hncu.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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

    /**
     * 新增商品类目
     * @param category 商品类目对象
     * @return
     */
    @ApiOperation("新增商品类目")
    @PostMapping
    @PreAuthorize("hasAuthority('prod:category:save')")
    public Result<String> saveCategory(@RequestBody Category category){
        Boolean saved = categoryService.saveCategory(category);
        return Result.handle(saved);
    }

    /**
     * 根据标识查询商品类目详情
     * @param categoryId
     * @return
     */
    @ApiOperation("根据标识查询商品类目详情")
    @GetMapping("info/{categoryId}")
    @PreAuthorize("hasAuthority('prod:category:info')")
    public Result<Category> loadCategoryInfo(@PathVariable Long categoryId){
        Category category = categoryService.getById(categoryId);
        return Result.success(category);
    }

    /**
     * 修改商品类目信息
     * @param category
     * @return
     */
    @ApiOperation("修改商品类目信息")
    @PutMapping("")
    @PreAuthorize("hasAuthority('prod:category:update')")
    public Result<String> modifyCategory(@RequestBody Category category){
        Boolean modified = categoryService.modifyCategory(category);
        return Result.handle(modified);
    }

    /**
     * 删除商品类目
     * @param categoryId 商品类目标识
     * @return
     */
    @ApiOperation("删除商品类目")
    @DeleteMapping("{categoryId}")
    @PreAuthorize("hasAuthority('prod:category:delete')")
    public Result<String> removeCategory(@PathVariable Long categoryId){
        Boolean removed = categoryService.removeCategoryById(categoryId);
        return Result.handle(removed);
    }

    ////////////微信小程序数据接口//////////////////

    /**
     * 微信小程序中商品的一级类目集合
     * @param parentId 父节点id
     * @return
     */
    @ApiOperation("微信小程序中商品的一级类目集合")
    @GetMapping("category/list")
    public Result<List<Category>> loadWxCategoryList(@RequestParam Long parentId){
        List<Category> categories = categoryService.queryWxCategoryListByPid(parentId);
        return Result.success(categories);
    }

    ////////////////////feign接口/////////////////////
    @GetMapping("prod/category/getCategoryListByParentId")
    public Result<List<Category>> getCategoryListByParentId(@RequestParam Long parentId){
        //查询商品的子类目集合
        List<Category> list = categoryService.list(new LambdaQueryWrapper<Category>()
                .eq(Category::getParentId, parentId));

        return Result.success(list);
    };


}
