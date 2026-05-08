package com.thang.order_service.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
public class OrderResponse {
    private UUID id;
    private UUID customerId;
    private String status;
    private BigDecimal totalAmount;
    private List<OrderItemResponse> items;
    private Instant createdAt;
    private Instant updatedAt;
}
