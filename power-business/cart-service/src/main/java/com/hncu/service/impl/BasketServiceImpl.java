package com.hncu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hncu.domain.Basket;
import com.hncu.mapper.BasketMapper;
import com.hncu.model.CartItem;
import com.hncu.model.ShopCart;
import com.hncu.service.BasketService;
import com.hncu.util.AuthUtils;
import com.hncu.vo.CartVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    @Override
    public CartVo queryMemberVo() {
        //从容器中获取会员会员openId
        String openId = AuthUtils.getMemberOpenId();
        //创建购物车对象
        CartVo cartVo = new CartVo();
        //查询会员购物车记录
        List<Basket> basketList = basketMapper.selectList(new LambdaQueryWrapper<Basket>()
                .eq(Basket::getOpenId, openId)
        );
        //判断购物车记录是否有值
        if (CollectionUtils.isEmpty(basketList)) {
            //当前购物车记录没有数据，直接返回
            return cartVo;
        }
        //从购物车记录集合中获取商品skuId集合
        List<Long> skuIdList = basketList.stream().map(Basket::getSkuId).collect(Collectors.toList());
        //远程调用，根据skuId结合查询上面sku对象集合

        //创建购物车店铺对象集合
        List<ShopCart> shopCartList =  new ArrayList<>();
        //循环遍历，会员购物车记录集合
        basketList.forEach(basket -> {
            //当前购物车所属的店铺标识
            Long shopId = basket.getShopId();
            //获取购物车Id
            Long basketId = basket.getBasketId();
            //获取商品Id
            Long prodId = basket.getProdId();
            //获取skuId
            Long skuId = basket.getSkuId();
            //获取商品购买数量
            Integer prodCount = basket.getProdCount();

            //创建购物车店铺对象
            ShopCart shopCart = new ShopCart();
            //创建购物车商品条目对象集合
            List<CartItem> cartItemList = new ArrayList<>();
            //创建购物车商品条目对象
            CartItem cartItem = new CartItem();
            //给购物车商品条目对象赋值
            cartItem.setBasketId(basketId);
            cartItem.setProdId(prodId);
            cartItem.setSkuId(skuId);
            cartItem.setProdCount(prodCount);
            //根据商品skuId查询商品条目对象


            cartItemList.add(cartItem);
            shopCart.setShopId(shopId);
            shopCart.setShopCartItems(cartItemList);
            shopCartList.add(shopCart);
        });




        cartVo.setShopCarts(shopCartList);
        return cartVo;
    }
}
