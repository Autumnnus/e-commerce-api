package com.kadir.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class DtoCategoryIU {

    @NotNull(message = "Name is required")
    private String name;

    private String description;
}
