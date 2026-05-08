package com.thang.product_service.entity;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
@EqualsAndHashCode
public class ProductCategoryId implements Serializable {
    private Long productId;
    private Long categoryId;
}
