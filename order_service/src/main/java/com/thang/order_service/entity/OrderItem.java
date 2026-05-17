package com.thang.order_service.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.sql.Types;
import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "order_items")
@EntityListeners(AuditingEntityListener.class)
public class OrderItem {

    @Id
    @JdbcTypeCode(Types.VARCHAR)
    private UUID id;

    @CreatedDate
    @Column(updatable = false)
    private Instant createdAt;

    @LastModifiedDate
    private Instant updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    @JsonIgnore
    private Order order;

    @Column(name = "product_id")
    @JdbcTypeCode(Types.VARCHAR)
    private UUID productId;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "unit_price", precision = 15, scale = 2)
    private BigDecimal unitPrice;

    private int quantity;

    @Column(precision = 15, scale = 2)
    private BigDecimal subtotal;

    @PrePersist
    protected void assignId() {
        if (this.id == null) {
            this.id = UUID.randomUUID();
        }
    }
}
