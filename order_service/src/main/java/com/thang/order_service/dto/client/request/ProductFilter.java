package com.thang.order_service.dto.client.request;

import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

@Getter
@Builder
public class ProductFilter {
    List<UUID> productIds;
}
