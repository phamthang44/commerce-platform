package com.thang.order_service.service.impl;

import com.thang.order_service.constant.ErrorCode;
import com.thang.order_service.dto.request.CreateOrderRequest;
import com.thang.order_service.dto.request.UpdateOrderStatusRequest;
import com.thang.order_service.dto.response.OrderResponse;
import com.thang.order_service.entity.Order;
import com.thang.order_service.entity.OrderItem;
import com.thang.order_service.exception.ApplicationException;
import com.thang.order_service.mapper.OrderMapper;
import com.thang.order_service.repository.OrderRepository;
import com.thang.order_service.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    @Override
    @Transactional
    public OrderResponse createOrder(CreateOrderRequest request) {
        log.info("Creating order for customer: {}", request.getCustomerId());

        List<OrderItem> items = request.getItems().stream()
                .map(itemReq -> {
                    BigDecimal subtotal = itemReq.getUnitPrice()
                            .multiply(BigDecimal.valueOf(itemReq.getQuantity()));
                    return OrderItem.builder()
                            .productId(itemReq.getProductId())
                            .productName(itemReq.getProductName())
                            .unitPrice(itemReq.getUnitPrice())
                            .quantity(itemReq.getQuantity())
                            .subtotal(subtotal)
                            .build();
                })
                .toList();

        BigDecimal totalAmount = items.stream()
                .map(OrderItem::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Order order = Order.builder()
                .customerId(request.getCustomerId())
                .status("PENDING")
                .totalAmount(totalAmount)
                .items(items)
                .build();

        items.forEach(item -> item.setOrder(order));

        return orderMapper.toOrderResponse(orderRepository.save(order));
    }

    @Override
    @Transactional(readOnly = true)
    public OrderResponse getOrderById(UUID id) {
        log.info("Fetching order: {}", id);
        return orderMapper.toOrderResponse(
                orderRepository.findById(id)
                        .orElseThrow(() -> new ApplicationException(ErrorCode.ORDER_NOT_FOUND)));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<OrderResponse> getAllOrders(int page, int size) {
        log.info("Fetching all orders page={} size={}", page, size);
        var pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        return orderRepository.findAll(pageable).map(orderMapper::toOrderResponse);
    }

    @Override
    @Transactional
    public OrderResponse updateOrderStatus(UUID id, UpdateOrderStatusRequest request) {
        log.info("Updating order {} status to {}", id, request.getStatus());
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ApplicationException(ErrorCode.ORDER_NOT_FOUND));
        order.setStatus(request.getStatus().toUpperCase());
        return orderMapper.toOrderResponse(orderRepository.save(order));
    }

    @Override
    @Transactional
    public void cancelOrder(UUID id) {
        log.info("Cancelling order: {}", id);
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ApplicationException(ErrorCode.ORDER_NOT_FOUND));
        if (!"PENDING".equals(order.getStatus())) {
            throw new ApplicationException(ErrorCode.ORDER_CANNOT_BE_CANCELLED);
        }
        order.setStatus("CANCELLED");
        orderRepository.save(order);
    }
}
