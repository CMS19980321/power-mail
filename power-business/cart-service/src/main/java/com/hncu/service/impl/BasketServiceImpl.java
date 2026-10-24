package com.hncu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hncu.constant.BusinessEnum;
import com.hncu.domain.Basket;
import com.hncu.domain.Sku;
import com.hncu.ex.handler.BusinessException;
import com.hncu.feign.BasketProdFeign;
import com.hncu.mapper.BasketMapper;
import com.hncu.model.CartItem;
import com.hncu.model.Result;
import com.hncu.model.ShopCart;
import com.hncu.service.BasketService;
import com.hncu.util.AuthUtils;
import com.hncu.vo.CartVo;
import org.springframework.beans.BeanUtils;
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

    @Autowired
    private BasketProdFeign basketProdFeign;


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
        Result<List<Sku>> result = basketProdFeign.getSKuListBySkuIds(skuIdList);
        //对操作结果进行判断
        if (result.getCode().equals(BusinessEnum.OPERATION_FAIL.getCode())) {
            throw new BusinessException("远程调用，根据skuId结合查询上面sku对象集合失败");
        }

        List<Sku> skuList = result.getData();

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

            //判断当前 购物车记录 所属店铺是否在购 物车店铺对象集合 中存在
            List<ShopCart> oneShopCart = shopCartList
                    .stream()
                    .filter(shopCart -> shopCart.getShopId().equals(shopId))
                    .collect(Collectors.toList());
            //判断是否有值
            if (CollectionUtils.isEmpty(oneShopCart)) {
                //集合为空说明 当前购物车记录 所属的店铺对象在 购物车物车店铺对象集合 不存在，需要创建购物车店铺对象
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
                //从商品sku对象集合中获取当前当前购物车记录所对象的sku对象
                Sku sku2 = skuList
                        .stream()
                        .filter(sku -> sku.getSkuId().equals(skuId))
                        .collect(Collectors.toList())
                        .get(0);
                //将sku2对象属性中的值赋值到购物车条目属性中去
                BeanUtils.copyProperties(sku2,cartItem);


                cartItemList.add(cartItem);
                shopCart.setShopId(shopId);
                shopCart.setShopCartItems(cartItemList);
                shopCartList.add(shopCart);
            } else {
                //集合不为空，说明 当前购物车记录 所属的店铺对象在 购物车物车店铺对象集合 存在，不需要创建购物车店铺对象，
                // 应该从购物车物车店铺对象集合中取出 对应购物车店铺对象

                //获取当前购物车记录属性中的购物车店铺对象
                ShopCart shopCart = oneShopCart.get(0);
                //获取 购物车店铺对象 之前的购物车商品条目对象集合
                List<CartItem> cartItemList = shopCart.getShopCartItems();
                //现在将新的 购物车条目对象 添加到之前的商品购物车商品条目对象集合中去
                //创建购物车商品条目对象
                CartItem cartItem = new CartItem();
                cartItem.setBasketId(basketId);
                cartItem.setProdId(prodId);
                cartItem.setSkuId(skuId);
                cartItem.setProdCount(prodCount);
                //从商品sku对象集合中获取当前当前购物车记录所对象的sku对象
                Sku sku2 = skuList
                        .stream()
                        .filter(sku -> sku.getSkuId().equals(skuId))
                        .collect(Collectors.toList())
                        .get(0);
                //将sku2对象属性中的值赋值到购物车条目属性中去
                BeanUtils.copyProperties(sku2,cartItem);

                cartItemList.add(cartItem);
                shopCart.setShopCartItems(cartItemList);
            }
        });

        cartVo.setShopCarts(shopCartList);
        return cartVo;
    }
}
