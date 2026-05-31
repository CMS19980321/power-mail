package com.hncu.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hncu.domain.Order;

import java.util.Date;

public interface OrderService extends IService<Order>{


    /**
     * 多条件分页查询订单
     * @param page
     * @param orderNumber
     * @param status
     * @param startTime
     * @param endTime
     * @return
     */
    Page<Order> queryOrderPage(Page<Order> page, String orderNumber, Integer status, Date startTime, Date endTime);

    /**
     * 根据订单编号查询订单详情
     * @param orderNumber
     * @return
     */
    Order queryOrderDetailByOrderNumber(Long orderNumber);


    /**
     * 分页查询会员订单列表
     * @param current
     * @param size
     * @param status
     * @return
     */
    Page<Order> queryMemberOrderPage(Long current, Long size, Long status);

    /**
     * 根据订单编号查询订单详情
     * @param orderNumber
     * @return
     */
    Order queryMemberOrderDetailByOrderNumber(String orderNumber);

    /**
     * 会员确认收货
     * @param orderNumber
     * @return
     */
    Boolean receiptMemberOrder(String orderNumber);

    /**
     * 删除会员订单
     * @param orderNumber
     * @return
     */
    Boolean removeMemberOrderByOrderNumber(String orderNumber);



}
