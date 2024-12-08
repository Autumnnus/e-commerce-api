package com.kadir.modules.discount.dto;

import com.kadir.common.dto.BaseDto;
import com.kadir.common.enums.DiscountType;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class DiscountCreateDto extends BaseDto {

    @NotNull(message = "Discount type cannot be null")
    private DiscountType discountType;

    @NotNull(message = "Discount cannot be null")
    @Min(value = 0, message = "Discount must be greater than 0")
    @Max(value = 100, message = "Discount must be less than 100")
    private BigDecimal discount;

    private Long productId;

    private Long categoryId;

    @NotNull(message = "Start date cannot be null")
    private LocalDateTime startDate;

    @NotNull(message = "End date cannot be null")
    private LocalDateTime endDate;
}
