package com.hncu.controller;

import com.hncu.model.Result;
import com.hncu.service.BasketService;
import com.hncu.util.AuthUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author caimeisahng
 * @Date 2026/10/5 20:33
 * @Version 1.0
 * 购物车业务控制层
 */

@RestController
@RequestMapping("p/shopCart")
@Api(tags = "购物车业务接口管理")
public class BaseKetController {
    @Autowired
    private BasketService basketService;

    @ApiOperation("查询会员购物车中的商品数量")
    @GetMapping("prodCount")
    public Result<Integer> loadMemberBasketProdCount(){
        String openId = AuthUtils.getMemberOpenId();
        Integer count = basketService.queryMemberBasketProdCount(openId);
        return Result.success(count);
    }
}
