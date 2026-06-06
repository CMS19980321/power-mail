package com.hncu.order;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hncu.domain.Order;
import com.hncu.model.Result;
import com.hncu.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @Author caimeisahng
 * @Date 2026/6/6 20:39
 * @Version 1.0
 * 订单业务控制层
 */

@RestController
@Api(tags = "订单业务接口管理")
@RequestMapping("order/order")
public class SysOrderController {
    private OrderService orderService;

    @ApiOperation("多条件分页查询订单")
    @GetMapping("page")
    @PreAuthorize("hasAuthority('order:order:page')")
    public Result<Page<Order>> loaOrderPage(@RequestParam Long current,
                                            @RequestParam Long size,
                                            @RequestParam(required = false) String orderNumber,
                                            @RequestParam(required = false) Integer status,
                                            @RequestParam(required = false) Date startTime,
                                            @RequestParam(required = false) Date endTime){
        Page<Order> page = new Page<>(current,size);
        page = orderService.queryOrderPage(page, orderNumber, status, startTime, endTime);

        return Result.success(page);
    }
}
