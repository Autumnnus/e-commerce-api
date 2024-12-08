package com.kadir.modules.coupon.dto;

import com.kadir.common.enums.DiscountType;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class CouponCreateDto {

    @NotNull(message = "Discount cannot be null")
    @Min(value = 0, message = "Discount must be greater than 0")
    @Max(value = 100, message = "Discount must be less than 100")
    private BigDecimal discount;

    @NotNull(message = "Code cannot be null")
    private String code;

    @NotNull(message = "Discount type cannot be null")
    private DiscountType discountType;

    private BigDecimal minOrderAmount = BigDecimal.ZERO;

    @NotNull(message = "Start date cannot be null")
    private LocalDateTime startDate;

    @NotNull(message = "End date cannot be null")
    private LocalDateTime endDate;

    private Long productId;

    private boolean isActive = true;
}
