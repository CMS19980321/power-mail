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
 * @Date 2026/10/31 20:49
 * @Version 1.0
 * 订单确认页面参数对象
 */

@ApiModel("订单确认页面参数对象")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderConfirmParam {
    /*
    * 接收请求来自商品详情页面的参数
    * */

    @ApiModelProperty("订单商品条目对象(接请求收来自商品详情页面的商品)")
    private OrderItem orderItem;
    @ApiModelProperty("购物车Id集合(接收请求来自与购物车页面的参数)")
    private List<Long> basketIds;

}
