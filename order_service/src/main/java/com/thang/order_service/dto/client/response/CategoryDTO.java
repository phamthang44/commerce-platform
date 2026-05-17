package com.thang.order_service.dto.client.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Builder
public class CategoryDTO {
    private UUID id;
    private String name;
    private String slug;
}
