package com.thang.product_service.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "products")
public class Product extends BaseEntity {

    private String name;

    private String slug;

    private String description;

    // The current selling price — what the customer pays.
    @Column(name = "price", precision = 15, scale = 2)
    private BigDecimal price;

    // The original price before discount, shown crossed-out in the UI ("Was ₫1,200,000").
    // Null means the product is not on sale.
    @Column(name = "compare_at_price", precision = 15, scale = 2)
    private BigDecimal compareAtPrice;

    @Column(name = "stock_quantity")
    private int stockQuantity;

    @Column(name = "status", length = 20)
    private String status;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<ProductCategory> categories;

}
