package com.thang.product_service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDetailResponse {
    private UUID id;
    private String name;
    private String slug;
    private Boolean isActive;
    private UUID parentId;
    private Instant createdAt;
    private Instant updatedAt;
}
