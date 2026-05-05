package com.hncu.controller;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hncu.domain.IndexImg;
import com.hncu.model.Result;
import com.hncu.service.IndexImgService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * @Author caimeisahng
 * @Date 2026/5/4 20:27
 * @Version 1.0
 * 轮播图业务控制层
 */


@Api(tags = "轮播图接口管理")
@RequestMapping("admin/indexImg")
@RestController
public class IndexImgController {
    @Autowired
    private IndexImgService indexImgService;


    /**
     * 多条件分页查询轮播图
     * @param current 页码
     * @param size 条数
     * @param status 轮播图状态
     * @return
     */

    @ApiOperation("多条件分页查询轮播图")
    @GetMapping("page")
    @PreAuthorize("hasAuthority('admin:indexImg:page')")
    public Result<Page<IndexImg>> loadIndexImgPage(@RequestParam Long current,
                                                   @RequestParam Long size,
                                                   @RequestParam(required = false) Integer status){
        //创建轮播图分页对象
        Page<IndexImg> page = new Page<>(current,size);
        //多条件分页查询轮播图
        page = indexImgService.page(page,new LambdaQueryWrapper<IndexImg>()
                .eq(ObjectUtil.isNotEmpty(status),IndexImg::getStatus,status)
                .orderByDesc(IndexImg::getSeq) //降序
        );

        return Result.success(page);
    }

    /**
     * 新增轮播图
     * @param indexImg 轮播图对象
     * @return
     */
    @ApiOperation("新增轮播图")
    @PostMapping("")
    @PreAuthorize("hasAuthority('admin:indexImg:save')")
    public Result<String> saveIndexImg(@RequestBody IndexImg indexImg){
        Boolean saved = indexImgService.saveIndexImg(indexImg);
        return Result.handle(saved);
    }

    @ApiOperation("根据标识查询轮播图信息")
    @GetMapping("info/[imgId]")
    @PreAuthorize("hasAuthority('admin:indexImg:info')")
    public Result<IndexImg> loadIndexImgInfo(@PathVariable Long imgId){
        IndexImg indexImg = indexImgService.queryIndexImgInfoById(imgId);
        return Result.success(indexImg);
    }
}
