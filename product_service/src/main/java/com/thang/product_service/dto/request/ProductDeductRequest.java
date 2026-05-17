package com.thang.product_service.dto.request;


import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class ProductDeductRequest {
    private UUID productId;
    private int quantity;
}
