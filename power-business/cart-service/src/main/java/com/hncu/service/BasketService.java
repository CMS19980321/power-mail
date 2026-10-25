package com.hncu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hncu.domain.Basket;
import com.hncu.vo.CartTotalAmount;
import com.hncu.vo.CartVo;

import java.util.List;

public interface BasketService extends IService<Basket>{


    /**
     * 查询会员购物车中商品数量
     * @param openId
     * @return
     */
    Integer queryMemberBasketProdCount(String openId);

    /**
     * 添加商品到购物车或修改商品在购物车中的数量
     * @param basket
     * @return
     */
    Boolean changeCartItem(Basket basket);

    CartVo queryMemberVo();

    CartTotalAmount calculateMemberCheckedBasketTotalAmount(List<Long> basketIds);
}
