package com.kadir.modules.discount.dto;

import com.kadir.common.dto.DtoBase;
import com.kadir.common.enums.DiscountType;
import com.kadir.modules.authentication.dto.UserDto;
import com.kadir.modules.category.dto.CategoryDto;
import com.kadir.modules.product.dto.ProductDto;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class DiscountDto extends DtoBase {

    @Enumerated(EnumType.STRING)
    private DiscountType discountType;

    private BigDecimal discountValue;

    private ProductDto product;

    private CategoryDto category;

    private UserDto user;

    private LocalDateTime startDate;

    private LocalDateTime endDate;
}
