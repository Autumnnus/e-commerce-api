package com.kadir.dto;


import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class DtoProductIU {

    @NotNull(message = "Name is required")
    private String name;


    private String description;

    private BigDecimal price;

    private int stockQuantity;

    private Long categoryId;
}
