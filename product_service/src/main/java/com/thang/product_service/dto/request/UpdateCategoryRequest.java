package com.thang.product_service.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UpdateCategoryRequest {

    @NotBlank
    private String name;

    @NotNull
    private Boolean isActive;

    private UUID parentId;
}
