package com.kadir.modules.product.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductUpdateDto {

    private String name;

    private String description;

    private BigDecimal price = BigDecimal.ZERO;

    private int stockQuantity = 0;

    private Long categoryId;

}
