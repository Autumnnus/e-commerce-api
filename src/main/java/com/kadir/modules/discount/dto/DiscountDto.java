package com.kadir.modules.discount.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.kadir.common.dto.DtoBase;
import com.kadir.common.enums.DiscountType;
import com.kadir.modules.authentication.dto.UserDto;
import com.kadir.modules.category.dto.CategoryDto;
import com.kadir.modules.product.dto.ProductDto;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class DiscountDto extends DtoBase {

    private DiscountType discountType;

    private BigDecimal discount;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private ProductDto product;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private CategoryDto category;

    private UserDto user;

    private LocalDateTime startDate;

    private LocalDateTime endDate;
}
