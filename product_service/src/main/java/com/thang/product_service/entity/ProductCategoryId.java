package com.thang.product_service.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;

import java.io.Serializable;
import java.sql.Types;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
@EqualsAndHashCode
public class ProductCategoryId implements Serializable {

    @Column(name = "product_id")
    @JdbcTypeCode(Types.VARCHAR)
    private UUID productId;

    @Column(name = "category_id")
    @JdbcTypeCode(Types.VARCHAR)
    private UUID categoryId;
}
