package com.hncu.service.impl;

import cn.hutool.core.util.ObjectUtil;
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
import com.hncu.vo.CartTotalAmount;
import com.hncu.vo.CartVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
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
        //获取会员openId
        String openId = AuthUtils.getMemberOpenId();
        //根据会员openId和商品库存skuId查询唯的购物车记录
        Basket beforeBasket = basketMapper.selectOne(new LambdaQueryWrapper<Basket>()
                .eq(Basket::getOpenId, openId)
                .eq(Basket::getSkuId, basket.getSkuId())
        );
        //判断购物车记录是否有值
        if (ObjectUtil.isNotNull(beforeBasket)) {
            //购物车记录不为空 -> 当前会员添加到购物车中的商品是存在的 -> 修改存在购物车中商品的数量
            //计算商品的最终数量
            int finalCount = beforeBasket.getProdCount() + basket.getProdCount();
            beforeBasket.setProdCount(finalCount);
            return basketMapper.updateById(beforeBasket) > 0;
        }
        //购物车记录为空 -> 当前会员添加到购物车中的商品是存在的 -> 添加存在购物车中商品的数量
        basket.setCreateTime(new Date());
        basket.setOpenId(openId);
        return basketMapper.insert(basket) > 0;
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
        //循环遍历，获取会员购物车记录
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

    @Override
    public CartTotalAmount calculateMemberCheckedBasketTotalAmount(List<Long> basketIds) {
        //创建购物车总金额对象
        CartTotalAmount cartTotalAmount = new CartTotalAmount();
        //判断会员是否有选中的购物车记录
        if (CollectionUtils.isEmpty(basketIds)) {
            //购物车Id集合为空-->说明会员没有选中的购物车记录-->购物车商品总金额为0
            return cartTotalAmount;
        }
        //购物车Id集合不为空-->说明会员没没有选中的购物车记录-->计算金额
        //根据购物车id集合查询购物车对象集合
        List<Basket> basketList = basketMapper.selectBatchIds(basketIds);
        //从购物车对象集合中获取商品skuId集合
        List<Long> skuIdList = basketList.stream().map(Basket::getSkuId).collect(Collectors.toList());
        //远程调用，根据商品skuId集合查询商品库存sku集合
        Result<List<Sku>> result = basketProdFeign.getSKuListBySkuIds(skuIdList);
        //判断查询结果
        if (result.getCode().equals(BusinessEnum.OPERATION_FAIL.getCode())) {
            throw new RuntimeException("远程调用，根据商品skuId集合查询商品库存sku集合失败");
        }
        //获取返回的数据
        List<Sku> skuList = result.getData();
        //计算所有单个商品总金额的集合
        ArrayList<BigDecimal> oneSkuTotalAmounts = new ArrayList<>();

        //循环遍历购物车对象结合
        basketList.forEach(basket -> {
            //获取购物车记录的商品skuId
            Long skuId = basket.getSkuId();
            //获取购物车记录中商品购买的数量
            Integer prodCount = basket.getProdCount();
            //从商品库存sku对象集合中获取与当前购物测记录的skuId一样的商品库存sku对象
            Sku sku2 = skuList
                    .stream()
                    .filter(sku -> skuId.equals(sku.getSkuId()))
                    .collect(Collectors.toList()).get(0);

            //获取商品单价
            BigDecimal price = sku2.getPrice();
            //及算单个商品的总金额
            BigDecimal oneSkuTotalAmount = price.multiply(new BigDecimal(prodCount));
            oneSkuTotalAmounts.add(oneSkuTotalAmount);

        });
        BigDecimal allSkuTotalAmount = oneSkuTotalAmounts.stream().reduce(BigDecimal::add).get();
        //填充数据
        cartTotalAmount.setTotalMoney(allSkuTotalAmount);
        cartTotalAmount.setFinalMoney(allSkuTotalAmount);
        //运费:购物测商品总金额超99元，免运费，如果小于99元，运费6元
        //默认实现的compareTo函数
        // -1是 allSkuTotalAmount <  99 的情况
        if (allSkuTotalAmount.compareTo(new BigDecimal(99)) == -1) {
            cartTotalAmount.setTransMoney(new BigDecimal(6));
            cartTotalAmount.setFinalMoney(allSkuTotalAmount.add(new BigDecimal(6)));
        }

        return cartTotalAmount;
    }
}
