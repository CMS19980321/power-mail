package com.hncu.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hncu.domain.Order;
import com.hncu.mapper.OrderItemMapper;
import com.hncu.mapper.OrderMapper;
import com.hncu.service.OrderItemService;
import com.hncu.service.OrderService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;

@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderItemMapper orderItemMapper;


    @Autowired
    private OrderItemService orderItemService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public Page<Order> queryOrderPage(Page<Order> page, String orderNumber, Integer status, Date startTime, Date endTime) {
        //多条件分页查询订单
        page = orderMapper.selectPage(page,new LambdaQueryWrapper<Order>()
                .eq(ObjectUtil.isNotNull(status),Order::getStatus,status)
                .between(ObjectUtil.isAllNotEmpty(startTime,endTime),Order::getCreateTime,startTime,endTime)
                .eq(StringUtils.hasText(orderNumber),Order::getOrderNumber,orderNumber)
                .orderByDesc(Order::getCreateTime)
        );
        return page;
    }

    @Override
    public Order queryOrderDetailByOrderNumber(Long orderNumber) {


        return null;
    }


    @Override
    public Page<Order> queryMemberOrderPage(Long current, Long size, Long status) {
        return null;
    }

    @Override
    public Order queryMemberOrderDetailByOrderNumber(String orderNumber) {

        return null;
    }

    @Override
    public Boolean receiptMemberOrder(String orderNumber) {
        return null;
    }

    @Override
    public Boolean removeMemberOrderByOrderNumber(String orderNumber) {
        return null;
    }
}


















