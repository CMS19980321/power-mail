package com.hncu.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hncu.constant.BusinessEnum;
import com.hncu.domain.MemberAddr;
import com.hncu.domain.Order;
import com.hncu.domain.OrderItem;
import com.hncu.ex.handler.BusinessException;
import com.hncu.feign.OrderMemberFeign;
import com.hncu.mapper.OrderItemMapper;
import com.hncu.mapper.OrderMapper;
import com.hncu.model.Result;
import com.hncu.service.OrderItemService;
import com.hncu.service.OrderService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderItemMapper orderItemMapper;

    @Autowired
    private OrderMemberFeign orderMemberFeign;


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
        //从订单分页对象中获取订单分页记录
        List<Order> orderList = page.getRecords();
        //判断是否有值
        if (CollectionUtils.isEmpty(orderList)) {
            return page;
        }
        //从订单记录中获取获取订单编号集合
        List<String> orderNumberList = orderList.stream().map(Order::getOrderNumber).collect(Collectors.toList());
        //根据订单编号查询所有订单商品条目对象集合
        List<OrderItem> orderItemList = orderItemMapper.selectList(new LambdaQueryWrapper<OrderItem>()
                .in(OrderItem::getOrderNumber, orderNumberList));


        //循环遍历订单记录集合
        orderList.forEach(order -> {
            //从订单商品条目对象集合中过滤出与当前订单记录编号一致的商品条目对象集合
            //orderItemList 是一个 List<OrderItem> 类型的集合，存储了订单明细对象。
            //.stream() 将列表转换为一个流，以便进行函数式操作（如过滤、映射等）。
            //.filter(...) 是流的中间操作，用于筛选符合条件的元素。
            //orderItem -> ... 是 Lambda 表达式，参数 orderItem 代表流中的每一个 OrderItem 对象。
            List<OrderItem> itemList = orderItemList.stream()
                    .filter(orderItem -> orderItem.getOrderNumber().equals(order.getOrderNumber()))
                    .collect(Collectors.toList());

            order.setOrderItems(itemList);
        });


        return page;
    }

    @Override
    public Order queryOrderDetailByOrderNumber(Long orderNumber) {
        //根据订单编号查询订单信息
        Order order = orderMapper.selectOne(new LambdaQueryWrapper<Order>()
                .eq(Order::getOrderNumber, orderNumber));
        //根据订单编号查询订单商品条目对象集合
        List<OrderItem> orderItemList = orderItemMapper.selectList(new LambdaQueryWrapper<OrderItem>()
                .eq(OrderItem::getOrderNumber, orderNumber));
        order.setOrderItems(orderItemList);
        //从订单记录中获取订单收货标识
        Long addrOrderId = order.getAddrOrderId();
        //远程调用，根据守护地址标识查询地址详情
        Result<MemberAddr> result = orderMemberFeign.getMemberAddrById(addrOrderId);
        //判断结果
        if (result.getCode().equals(BusinessEnum.OPERATION_FAIL.getCode())) {
            throw new BusinessException("远程接口调用失败，根据收货地址标识查询收货地址信息");
        }

        //获取数据
        MemberAddr memberAddr = result.getData();
        order.setUserAddrOrder(memberAddr);

        //远程接口调用，根据openId查询会员昵称
        Result<String> result12 = orderMemberFeign.getNickNameByOpenId(order.getOpenId());
        if (result12.getCode().equals(BusinessEnum.OPERATION_FAIL.getCode())) {
            throw new BusinessException("远程接口调用失败，根据会员openId查询会员昵称");
        }
        //获取数据
        String nickName = result12.getData();
        order.setNickName(nickName);


        return order;
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


















