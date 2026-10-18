package com.hncu.model;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @Author caimeisahng
 * @Date 2026/10/7 16:55
 * @Version 1.0
 */


@Api(tags = "购物车商品")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class CartItem {
    @ApiModelProperty("商品Id")
    private Long prodId;
    @ApiModelProperty("商品skuId")
    private Long skuId;
    @ApiModelProperty("商品图片")
    private String pic;
    @ApiModelProperty("商品名称")
    private String prodName;
    @ApiModelProperty("商品sku名称")
    private String skuName;
    @ApiModelProperty("商品党家")
    private BigDecimal price;
    @ApiModelProperty("购物车Id")
    private Long basketId;
    @ApiModelProperty("商品购买数量")
    private Integer prodCount;


}
