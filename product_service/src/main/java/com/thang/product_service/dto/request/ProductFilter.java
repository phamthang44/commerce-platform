package com.thang.product_service.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Builder
@Getter
@Setter
public class ProductFilter {
    private List<UUID> productIds;
}
