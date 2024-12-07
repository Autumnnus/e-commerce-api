package com.kadir.modules.coupon.model;

import com.kadir.common.enums.DiscountType;
import com.kadir.common.model.BaseEntity;
import com.kadir.modules.authentication.model.User;
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

    @Column(name = "discount", nullable = false)
    private double discount;

    @Enumerated(EnumType.STRING)
    @Column(name = "discount_type", nullable = false)
    private DiscountType discountType;

    @Column(name = "discount_value", nullable = true)
    private BigDecimal discountValue;

    @Column(name = "min_order_amount", nullable = true)
    private BigDecimal minOrderAmount;

    @Column(name = "max_discount_value", nullable = true)
    private BigDecimal maxDiscountValue;

    @Column(name = "start_date", nullable = false)
    private LocalDateTime startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDateTime endDate;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private boolean isActive = true;
}
