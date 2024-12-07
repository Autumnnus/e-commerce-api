package com.kadir.modules.coupon.dto;

import com.kadir.common.dto.DtoBase;
import com.kadir.common.enums.DiscountType;
import com.kadir.modules.authentication.dto.UserDto;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class CouponDto extends DtoBase {
    private String code;

    private double discount;

    @Enumerated(EnumType.STRING)
    private DiscountType discountType;

    private BigDecimal discountValue;

    private BigDecimal minOrderAmount;

    private BigDecimal maxDiscountValue;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private UserDto user;

    private boolean isActive = true;
}
