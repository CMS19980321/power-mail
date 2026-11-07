package com.hncu.vo;

import com.hncu.domain.MemberAddr;
import com.hncu.model.ShopOrder;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
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
    @ApiModelProperty("会员收货地址对象")
    private MemberAddr memberAddr;

    @ApiModelProperty("订单店铺对象集合")
    private List<ShopOrder> shopCartOrders;

    @ApiModelProperty("商品总数量")
    private Integer totalCount;

    @ApiModelProperty("合计|订单总额")
    private BigDecimal total = BigDecimal.ZERO;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("运费")
    private BigDecimal transfee = BigDecimal.ZERO;

    @ApiModelProperty("优惠金额")
    private BigDecimal shopReduce = BigDecimal.ZERO;

}
