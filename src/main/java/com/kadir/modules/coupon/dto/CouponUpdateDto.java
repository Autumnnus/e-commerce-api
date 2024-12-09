package com.kadir.modules.coupon.dto;

import com.kadir.common.enums.DiscountType;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class CouponUpdateDto {

    @Min(value = 0, message = "Discount must be greater than 0")
    @Max(value = 100, message = "Discount must be less than 100")
    private BigDecimal discount;

    private String code;

    private DiscountType discountType;

    private BigDecimal minOrderAmount = BigDecimal.ZERO;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private boolean isActive = true;
}
