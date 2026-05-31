package com.hncu.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hncu.domain.OrderItem;
import com.hncu.mapper.OrderItemMapper;
import com.hncu.service.OrderItemService;
import org.springframework.stereotype.Service;
@Service
public class OrderItemServiceImpl extends ServiceImpl<OrderItemMapper, OrderItem> implements OrderItemService{

}
