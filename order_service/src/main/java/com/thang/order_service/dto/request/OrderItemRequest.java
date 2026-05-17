package com.thang.order_service.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class OrderItemRequest {

    @NotNull
    private UUID productId;

    @Min(1)
    private int quantity;
}
