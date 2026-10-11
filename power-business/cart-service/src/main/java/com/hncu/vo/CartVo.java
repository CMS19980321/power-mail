package com.hncu.vo;

/**
 * @Author caimeisahng
 * @Date 2026/10/11 19:59
 * @Version 1.0
 * 购物车对象结合
 */

import com.hncu.model.ShopCart;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author caimeisahng
 * @Date 2026/10/11 19:52
 * @Version 1.0
 * 购物车店铺对象
 */
@Api(tags = "购物车对象")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class CartVo {

    @ApiModelProperty("购物车店铺对象集合")
    private List<ShopCart> shopCarts;
}
