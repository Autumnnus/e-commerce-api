package com.kadir.dto;

import com.kadir.model.Category;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class DtoProduct extends DtoBase {

    private String name;

    private String description;

    private BigDecimal price;

    private int stockQuantity;

    private Category category;
}
