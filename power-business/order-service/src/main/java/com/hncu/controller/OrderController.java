package com.hncu.controller;

import com.hncu.model.Result;
import com.hncu.service.OrderService;
import com.hncu.vo.OrderStatusCount;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author caimeisahng
 * @Date 2026/7/18 21:23
 * @Version 1.0
 * 微信小程序订单业务控制层
 */

@Api("微信小程序订单接口管理")
@RequestMapping("p/myOrder")
@RestController
public class OrderController {
    @Autowired
    private OrderService orderService;

    /**
     *
     * @return
     * 查询会员订单各状态数量
     */
    @ApiOperation("查询会员订单各状态数量")
    @GetMapping("orderCount")
    public Result<OrderStatusCount> loadMemberOrderStatusCount(){
        OrderStatusCount orderStatusCount = orderService.queryMemberOrderStatusCount();
        return Result.success(orderStatusCount);
    }
}
