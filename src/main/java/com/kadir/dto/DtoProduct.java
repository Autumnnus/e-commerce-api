package com.kadir.dto;

import com.kadir.model.Category;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DtoProduct extends DtoBase {

    @NotNull(message = "Name is required")
    private String name;


    private String description;

    @NotNull(message = "Price is required")
    private double price;

    @NotNull(message = "Stock quantity is required")
    private int stockQuantity;

    private Category category;
}
