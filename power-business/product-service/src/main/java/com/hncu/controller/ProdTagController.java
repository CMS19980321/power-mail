package com.hncu.controller;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hncu.domain.ProdTag;
import com.hncu.domain.ProdTagReference;
import com.hncu.model.Result;
import com.hncu.service.ProdTagReferenceService;
import com.hncu.service.ProdTagService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *
 * @Author caimeisahng
 * @Date 2026/3/29 5:00
 * @Version 1.0
 */

@Api(tags = "分组标签接口管理")
@RequestMapping("prod/prodTag")
@RestController
public class ProdTagController {
    @Autowired
    private ProdTagService prodTagService;

    @Autowired
    private ProdTagReferenceService prodTagReferenceService;

    /**
     * 多条件分页查询分组标签
     * @param current 当前页
     * @param size 每页显示条数
     * @param title 分组标签页
     * @param status 状态
     * @return
     */
    @ApiOperation("多条件分页查询分组标签")
    @GetMapping("page")
    @PreAuthorize("hasAuthority('prod:prodTag:page')")
    public Result<Page<ProdTag>> loadProdTagPage(@RequestParam Long current,
                                                 @RequestParam Long size,
                                                 @RequestParam(required = false) String title,
                                                 @RequestParam(required = false) String status){
        //创建分页对象
        Page<ProdTag> page = new Page<>(current,size);
        //多条件分页查询分组标签
        page = prodTagService.page(page,new LambdaQueryWrapper<ProdTag>()
                .eq(ObjectUtil.isNotEmpty(status),ProdTag::getStatus,status)
                .like(StringUtils.hasText(title),ProdTag::getTitle,title)
                .orderByDesc(ProdTag::getSeq)
        );

        return Result.success(page);
    }

    /**
     * 新增商品分组标签
     * @param prodTag 商品分组标签对象
     * @return
     */

    @ApiOperation("新增商品分组标签")
    @PostMapping("")
    @PreAuthorize("hasAuthority('prod:prodTag:save')")
    public Result<String> saveProdTag(@RequestBody ProdTag prodTag){
        Boolean saved = prodTagService.saveProdTag(prodTag);
        return Result.handle(saved);
    }

    /**
     * 根据标识查询分组标签详情
     * @param tagId 分组标签标识
     * @return
     */
    @ApiOperation("根据标识查询分组标签详情")
    @GetMapping("info/{tagId}")
    @PreAuthorize("hasAuthority('prod:prodTag:info')")
    public Result<ProdTag> loadProdTagInfo(@PathVariable Long tagId){
        ProdTag prodTag = prodTagService.getById(tagId);
        return Result.success(prodTag);
    }

    /**
     * 修改商品分组标签信息
     * @param prodTag
     * @return
     */
    @ApiOperation("修改商品分组标签信息")
    @PutMapping("")
    @PreAuthorize("hasAuthority('prod:prodTag:update')")
    public Result<String> modifyProdTag(@RequestBody ProdTag prodTag){
        Boolean modified = prodTagService.modifyProdTag(prodTag);
        return Result.handle(modified);
    }

    /**
     * 根据标识删除商品分组标签
     * @param tagId 分组标签标识
     * @return
     */
    @ApiOperation("根据标识删除商品分组标签")
    @DeleteMapping("{tagId}")
    @PreAuthorize("hasAuthority('prod:prodTag:delete')")
    public Result<String> removeProdTag(@PathVariable Long tagId){
        boolean removed = prodTagService.removeById(tagId);
        return Result.handle(removed);
    }

    /**
     * 查询状态正常的商品分组标签集合
     * @return
     */
    @ApiOperation("查询状态正常的商品分组标签集合")
    @GetMapping("listTagList")
    @PreAuthorize("hasAuthority('prod:prodTag:page')")
    public Result<List<ProdTag>> loadProdTagList(){
        List<ProdTag> prodTags = prodTagService.queryProdTagList();
        return Result.success(prodTags);
    }

    ///////////////卫星小程序数据接口//////////////////
    @ApiOperation("查询小程序商品分组标签")
    @GetMapping("prodTagList")
    public Result<List<ProdTag>> loadWXProdTagList(){
        List<ProdTag> prodTags = prodTagService.queryWxProdTagList();
        return Result.success(prodTags);
    }

    //////////////feign接口////////////////////
    public Result<Page<ProdTagReference>> getProdTagReferencePageTagId(@RequestParam Long current,
                                                                @RequestParam Long size,
                                                                @RequestParam Long tagId){
        Page<ProdTagReference> prodTagReferencePage = new Page<>(current, size);
        //根据分页标识分页查询商品与分组标签关系记录
        prodTagReferencePage = prodTagReferenceService.page(prodTagReferencePage,new LambdaQueryWrapper<ProdTagReference>()
                .eq(ProdTagReference::getTagId,tagId)
                .orderByDesc(ProdTagReference::getCreateTime)
        );
        return Result.success(prodTagReferencePage);
    }
}
