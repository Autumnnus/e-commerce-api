package com.kadir.dto;

import com.kadir.model.Category;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DtoProduct extends DtoBase {

    private String name;

    private String description;

    private double price;

    private int stockQuantity;

    private Category category;
}
