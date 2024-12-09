package com.kadir.modules.coupon.model;

import com.kadir.common.enums.DiscountType;
import com.kadir.common.model.BaseEntity;
import com.kadir.modules.authentication.model.User;
import com.kadir.modules.product.model.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "coupon")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Coupon extends BaseEntity {

    @Column(name = "code", unique = true, nullable = false)
    private String code;

    @Column(name = "discount")
    private BigDecimal discount;

    @Enumerated(EnumType.STRING)
    @Column(name = "discount_type", nullable = false)
    private DiscountType discountType;

    @Column(name = "min_order_amount")
    private BigDecimal minOrderAmount;

    @Column(name = "start_date", nullable = false)
    private LocalDateTime startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDateTime endDate;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "product_id")
    private Product product;

    private boolean isActive = true;
}
