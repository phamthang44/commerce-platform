package com.thang.order_service.service;

import com.thang.order_service.dto.request.CreateOrderRequest;
import com.thang.order_service.entity.Order;

public interface OrderService {
    Order createOrder(CreateOrderRequest request);
}
