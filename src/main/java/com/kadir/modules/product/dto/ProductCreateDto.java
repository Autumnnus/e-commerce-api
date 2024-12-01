package com.kadir.modules.product.dto;


import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductCreateDto {

    @NotNull(message = "Name is required")
    private String name;

    private String description;

    private BigDecimal price = BigDecimal.ZERO;

    private int stockQuantity = 0;

    @NotNull(message = "Category is required")
    private Long categoryId;

}
