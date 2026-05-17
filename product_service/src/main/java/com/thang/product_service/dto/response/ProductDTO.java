package com.thang.product_service.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
public class ProductDTO {
    private UUID id;
    private String name;
    private String slug;
    private String description;
    private BigDecimal price;
    private BigDecimal compareAtPrice;
    private int stockQuantity;
    private String status;
    private List<CategoryDTO> categories;
}
