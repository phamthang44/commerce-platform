package com.thang.product_service.entity;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
@EqualsAndHashCode
public class ProductCategoryId implements Serializable {
    private UUID productId;
    private UUID categoryId;
}
