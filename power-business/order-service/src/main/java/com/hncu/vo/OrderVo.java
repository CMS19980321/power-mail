package com.hncu.vo;

import com.hncu.model.ShopOrder;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author caimeisahng
 * @Date 2026/10/31 21:12
 * @Version 1.0
 * 订单确认页面对象
 */

@ApiModel("订单确认页面对象")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderVo {
    @ApiModelProperty("订单店铺对象集合")
    private List<ShopOrder> shopCartOrders;
}
