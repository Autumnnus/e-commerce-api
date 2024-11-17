package com.kadir.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DtoCategory extends DtoBase {

    @NotNull(message = "Name is required")
    private String name;

    private String description;
}
