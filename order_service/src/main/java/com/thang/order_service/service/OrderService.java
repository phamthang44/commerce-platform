package com.thang.order_service.service;

import com.thang.order_service.dto.request.CreateOrderRequest;
import com.thang.order_service.dto.response.OrderResponse;

import java.util.UUID;

public interface OrderService {
    OrderResponse createOrder(CreateOrderRequest request);
    OrderResponse getOrderById(UUID id);
}
