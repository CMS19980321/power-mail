package com.hncu.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hncu.domain.Prod;
import com.hncu.model.Result;
import com.hncu.service.MemberCollectionService;
import com.hncu.util.AuthUtils;
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
 * @Date 2026/7/25 19:56
 * @Version 1.0
 * 会员收藏商品业务控制层
 */

@RestController
@RequestMapping("p/collection")
@Api(tags = "会员收藏商品接口管理")

public class MemberCollectionController {
    @Autowired
    private MemberCollectionService memberCollectionService;

    /**
     * 查询会员收藏商品数量
     * @return
     */
    @ApiOperation("查询会员收藏商品数量")
    @GetMapping("count")
    public Result<Long> loadMemberCollectionProdCount(){
        Long count = memberCollectionService.queryMemberCollectionProdCount();
        return Result.success(count);
    }


    @ApiOperation("分页查询会员收藏列表")
    @GetMapping("prods")
    public Result<Page<Prod>> loadMemberCollectionProdPage(@RequestParam Long current,
                                                           @RequestParam Long size
                                                           ){
        //通过会员openId分页查询会员收藏列表
        Page<Prod> page = memberCollectionService.queryMemberCollectionProdPageByOpenId(AuthUtils.getMemberOpenId(),current,size);
        return Result.success(page);
    }
}
