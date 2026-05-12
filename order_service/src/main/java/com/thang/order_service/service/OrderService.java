package com.thang.order_service.service;

import com.thang.order_service.dto.request.CreateOrderRequest;
import com.thang.order_service.dto.request.UpdateOrderStatusRequest;
import com.thang.order_service.dto.response.OrderResponse;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface OrderService {
    OrderResponse createOrder(CreateOrderRequest request);
    OrderResponse getOrderById(UUID id);
    Page<OrderResponse> getAllOrders(int page, int size);
    OrderResponse updateOrderStatus(UUID id, UpdateOrderStatusRequest request);
    void cancelOrder(UUID id);
}
