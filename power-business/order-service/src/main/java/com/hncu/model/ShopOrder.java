package com.hncu.model;

import com.hncu.domain.OrderItem;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author caimeisahng
 * @Date 2026/10/31 21:00
 * @Version 1.0
 * 订单店铺对象
 */


@ApiModel("订单店铺对象")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShopOrder {
    @ApiModelProperty("店铺标识")
    private Long shopId;
    @ApiModelProperty("订单商品条目对象集合")
    private List<OrderItem> shopOrderItem;
}
