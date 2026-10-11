package com.hncu.model;

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

@Api(tags = "购物车店铺对象")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ShopCart {
    @ApiModelProperty("店铺标识")
    private Long shopId;
    @ApiModelProperty("商品条目对象集合")
    private List<CartItem> shopCartItems;
}
