package com.hncu.controller;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hncu.domain.Order;
import com.hncu.model.Result;
import com.hncu.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

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

    @Autowired
    private OrderService orderService;

    /**
     * 多条件分页查询订单
     * @param current 页码
     * @param size 每页显示条数
     * @param orderNumber 订单编号
     * @param status 订单状态
     * @param startTime 订单开始时间
     * @param endTime 订单结束时间
     * @return
     */

    @ApiOperation("多条件分页查询订单")
    @GetMapping("page")
    @PreAuthorize("hasAuthority('order:order:page')")
    public Result<Page<Order>> loaOrderPage(@RequestParam Long current,
                                            @RequestParam Long size,
                                            @RequestParam(required = false) String orderNumber,
                                            @RequestParam(required = false) Integer status,
                                            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date startTime,
                                            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date endTime){
        Page<Order> page = new Page<>(current,size);
        page = orderService.queryOrderPage(page, orderNumber, status, startTime, endTime);

        return Result.success(page);
    }


    /**
     * 根据订单编号查询订单详情
     * @param orderNumber 订单编号
     * @return
     */
    @ApiOperation("根据订单编号查询订单详情")
    @GetMapping("orderInfo/{orderNumber}")
    @PreAuthorize("hasAuthority('order:order:info')")
    public Result<Order> loadOrderDetail(@PathVariable Long orderNumber){
        Order order = orderService.queryOrderDetailByOrderNumber(orderNumber);
        return Result.success(order);
    }

    /**
     * 导出销售记录接口
     * @return
     */
    @ApiOperation("导出销售记录")
    @GetMapping("soldExcel")
    @PreAuthorize("hasAuthority('order:order:soldExcel')")
    public Result<String> exportSoleOrderRecordExcel(){
        List<Order> list = orderService.list(new LambdaQueryWrapper<Order>()
                .orderByDesc(Order::getCreateTime)
        );
        // 写法2
        String fileName ="D:\\bjpowernode\\Excel\\"  + System.currentTimeMillis() + ".xlsx";
        // 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        // 如果这里想使用03 则 传入excelType参数即可
        EasyExcel.write(fileName, Order.class).sheet("模板111").doWrite(list);

        return Result.success(null);
    }
}
