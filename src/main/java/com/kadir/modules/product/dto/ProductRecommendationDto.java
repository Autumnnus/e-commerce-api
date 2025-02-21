package com.kadir.modules.product.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductRecommendationDto {
    private Long id;
    private String name;
    private BigDecimal price;
    private String reasonsToBuy;
    private String reasonsToAvoid;
    private String comparison;
}

