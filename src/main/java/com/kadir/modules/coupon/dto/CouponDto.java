package com.kadir.modules.coupon.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.kadir.common.dto.BaseDto;
import com.kadir.common.enums.DiscountType;
import com.kadir.modules.authentication.dto.UserDto;
import com.kadir.modules.product.dto.ProductDto;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class CouponDto extends BaseDto {
    private String code;

    private BigDecimal discount;

    private DiscountType discountType;

    private BigDecimal discountValue;

    private BigDecimal minOrderAmount;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private UserDto user;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private ProductDto product;

    private boolean isActive = true;
}
