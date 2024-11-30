package com.kadir.modules.orderitems.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kadir.common.model.BaseEntity;
import com.kadir.modules.order.model.Order;
import com.kadir.modules.product.model.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "order_items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderItems extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    @JsonIgnore
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    @JsonIgnore
    private Product product;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "price", nullable = false)
    private BigDecimal price = BigDecimal.ZERO;
}
