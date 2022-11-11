package com.duang.web.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.duang.cloudcommons.entity.Order;
import com.duang.web.mapper.OrderMapper;
import com.duang.web.service.OrderService;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {
}
