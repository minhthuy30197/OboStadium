package com.company.demo.service.impl;

import com.company.demo.entity.Order;
import com.company.demo.entity.Product;
import com.company.demo.entity.User;
import com.company.demo.model.request.CreateOrderReq;
import com.company.demo.repository.OrderRepository;
import com.company.demo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.sql.Timestamp;

@Component
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Override
    public Order createOrder(CreateOrderReq req, Long userId) {
        Order order = new Order();

        order.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        User createdBy = new User();
        createdBy.setId(userId);
        order.setCreatedBy(createdBy);
        order.setReceiverAddress(req.getReceiverAddress());
        order.setReceiverName(req.getReceiverName());
        order.setReceiverPhone(req.getReceiverPhone());
        Product product = new Product();
        product.setId(req.getProductId());
        order.setProduct(product);

        orderRepository.save(order);

        return order;
    }
}
