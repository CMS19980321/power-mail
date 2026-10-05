package com.hncu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hncu.domain.Basket;
import com.hncu.mapper.BasketMapper;
import com.hncu.service.BasketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BasketServiceImpl extends ServiceImpl<BasketMapper, Basket> implements BasketService{

    @Autowired
    private BasketMapper basketMapper;


    /**
     * 会员购物车中商品的数量计算方法：所有商品数量的和
     * @param openId
     * @return
     */
    @Override
    public Integer queryMemberBasketProdCount(String openId) {

        /*LambdaQueryWrapper 的 select 只能基于实体类的字段引用
        （如 Basket::getProdCount），它无法表达 ifnull(sum(...), 0) 这种 SQL 表达式*/

        List<Object> objs = basketMapper.selectObjs(new QueryWrapper<Basket>()
                .select("ifnull(sum(prod_count),0)")
                .eq("open_id", openId)
        );
        Object o = objs.get(0);
        return Integer.valueOf(o.toString());
    }

    @Override
    public Boolean changeCartItem(Basket basket) {
        return null;
    }
}
