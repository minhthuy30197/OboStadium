package com.company.demo.service;

import com.company.demo.entity.Order;
import com.company.demo.model.request.CreateOrderReq;
import org.springframework.stereotype.Service;

@Service
public interface OrderService {
    public Order createOrder(CreateOrderReq req, Long userId);
}
