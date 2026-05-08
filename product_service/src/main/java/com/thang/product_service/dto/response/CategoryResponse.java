package com.thang.product_service.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Builder
public class CategoryResponse {
    private UUID id;
    private String name;
    private String slug;
}
