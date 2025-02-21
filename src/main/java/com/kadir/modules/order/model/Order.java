package com.kadir.modules.order.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kadir.common.enums.OrderStatus;
import com.kadir.common.model.BaseEntity;
import com.kadir.modules.authentication.model.Customer;
import com.kadir.modules.orderitems.model.OrderItems;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "order")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Order extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<OrderItems> orderItems;

    @Column(name = "order_date", nullable = false)
    private LocalDateTime orderDate = LocalDateTime.now();

    @Column(name = "total_amount", nullable = false)
    private BigDecimal totalAmount = BigDecimal.ZERO;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status")
    private OrderStatus paymentStatus;

    @Column(name = "payment_method")
    private String paymentMethod; // Ödeme yöntemi (örneğin, "Credit Card", "PayPal")
}
