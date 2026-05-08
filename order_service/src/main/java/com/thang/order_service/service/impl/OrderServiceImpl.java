package com.thang.order_service.service.impl;

import com.thang.order_service.dto.request.CreateOrderRequest;
import com.thang.order_service.entity.Order;
import com.thang.order_service.entity.OrderItem;
import com.thang.order_service.repository.OrderRepository;
import com.thang.order_service.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    @Override
    @Transactional
    public Order createOrder(CreateOrderRequest request) {
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

        // Set the back-reference so Hibernate can cascade the insert to order_items
        items.forEach(item -> item.setOrder(order));

        return orderRepository.save(order);
    }
}
