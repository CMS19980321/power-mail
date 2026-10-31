package com.hncu.controller;

import com.hncu.domain.Basket;
import com.hncu.model.Result;
import com.hncu.service.BasketService;
import com.hncu.util.AuthUtils;
import com.hncu.vo.CartTotalAmount;
import com.hncu.vo.CartVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @ApiOperation("查询会员购物车数据")
    @GetMapping("info")
    public Result<CartVo> loadMemberCartVo(){
        CartVo cartVo = basketService.queryMemberVo();
        return Result.success(cartVo);
    }

    @ApiOperation("计算会员选中的商品的金额")
    @PostMapping("totalPay")
    public Result<CartTotalAmount> calculateMemberCheckedBasketTotalAmount(@RequestBody List<Long> basketIds){
        CartTotalAmount cartTotalAmount = basketService.calculateMemberCheckedBasketTotalAmount(basketIds);
        return Result.success(cartTotalAmount);
    }

    /**
     * 添加商品到购物车或者修改商品的数量
     * @param basket 购物车对象(shopId,prodId,skuId,prodCount)
     * @return
     */
    @ApiOperation("添加商品到购物车或者修改商品的数量")
    @PostMapping("changeItem")
    public Result<String> changeCartItem(@RequestBody Basket basket){
        Boolean changed = basketService.changeCartItem(basket);
        return Result.handle(changed);
    }

    @ApiOperation("删除会员选中的购物车记录")
    @DeleteMapping("deleteItem")
    public Result<String> removeMemberCheckedBasket(@RequestBody List<Long> basketIds){
        boolean removed = basketService.removeBatchByIds(basketIds);
        return Result.handle(removed);
    }
}
