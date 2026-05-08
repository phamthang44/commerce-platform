package com.thang.order_service.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
public class OrderItemRequest {

    @NotNull
    private UUID productId;

    // Snapshot: the caller fetches the product from product_service
    // and passes these values. The order_service stores them as-is.
    @NotBlank
    private String productName;

    @NotNull
    @DecimalMin("0.01")
    private BigDecimal unitPrice;

    @Min(1)
    private int quantity;
}
