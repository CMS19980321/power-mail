package com.hncu.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hncu.domain.Order;
import com.hncu.model.Result;
import com.hncu.service.OrderService;
import com.hncu.vo.OrderStatusCount;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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

    @ApiOperation("分页查询会员订单列表")
    @GetMapping("myOrder")
    public Result<Page<Order>> loadMemberOrderPage(@RequestParam Long current,
                                                   @RequestParam Long size,
                                                   @RequestParam Long status){
        Page<Order> page = orderService.queryMemberOrderPage(current, size, status);

        return Result.success(page);
    }

    /**
     *
     * @param orderNumber 订单编号
     * @return
     */

    @ApiOperation("根据订单编号查询订单详情")
    @GetMapping("orderDetail")
    public Result<Order> loadMemberOrderDetail(@RequestParam String orderNumber){
        Order order = orderService.queryMemberOrderDetailByOrderNumber(orderNumber);
        return Result.success(order);
    }

    @ApiOperation("会员确认收货")
    @PutMapping("receipt/{orderNumber}")
    public Result<String> receiptOrderNumber(@PathVariable String orderNumber){
        Boolean receipted = orderService.receiptMemberOrder(orderNumber);
        return Result.handle(receipted);
    }

    /**
     * 删除会员订单
     * @param orderNumber
     * @return
     */
    @ApiOperation("删除会员订单")
    @DeleteMapping("{orderNumber}")
    public Result<String> removeMemberOrder(@PathVariable String orderNumber){
        Boolean removed = orderService.removeMemberOrderByOrderNumber(orderNumber);
        return Result.handle(removed);
    }
}
