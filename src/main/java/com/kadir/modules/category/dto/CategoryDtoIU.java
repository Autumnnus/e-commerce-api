package com.kadir.modules.category.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryDtoIU {

    @NotNull(message = "Name is required")
    private String name;

    private String description;
}
