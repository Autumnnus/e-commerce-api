package com.kadir.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DtoProductIU {

    @NotNull(message = "Name is required")
    private String name;


    private String description;

    @NotNull(message = "Price is required")
    private double price;

    @NotNull(message = "Stock quantity is required")
    private int stockQuantity;

    private Long categoryId;
}
