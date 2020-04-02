package com.company.demo.service;

import com.company.demo.entity.Order;
import com.company.demo.model.dto.OrderDetailDto;
import com.company.demo.model.dto.OrderInfoDto;
import com.company.demo.model.request.CreateOrderReq;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public interface OrderService {
    public Order createOrder(CreateOrderReq req, long userId);

    public List<OrderInfoDto> getListOrderOfPersonByStatus(int status, long userId);

    public OrderDetailDto userGetDetailById(long id, long userId);

    public void userCancelOrder(long id, long userId);
}
