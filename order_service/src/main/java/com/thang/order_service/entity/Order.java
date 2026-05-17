package com.thang.order_service.entity;

import com.thang.order_service.common.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;

import java.math.BigDecimal;
import java.sql.Types;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders")
public class Order extends AuditableEntity {

    @Column(name = "customer_id")
    @JdbcTypeCode(Types.VARCHAR)
    private UUID customerId;

    @Column(length = 20)
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Column(name = "total_amount", precision = 15, scale = 2)
    private BigDecimal totalAmount;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> items;
}
