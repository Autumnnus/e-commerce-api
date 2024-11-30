package com.kadir.modules.category.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DtoCategoryIU {

    @NotNull(message = "Name is required")
    private String name;

    private String description;
}
