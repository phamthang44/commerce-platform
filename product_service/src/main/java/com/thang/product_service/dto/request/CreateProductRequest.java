package com.thang.product_service.dto.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
public class CreateProductRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String description;

    @NotNull
    private BigDecimal price;

    @NotNull
    @PositiveOrZero
    private int stockQuantity;

    @NotBlank
    private String status;

    @NotNull
    private UUID categoryId;
}
