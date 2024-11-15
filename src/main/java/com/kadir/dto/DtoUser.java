package com.kadir.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DtoUser extends DtoBase {

    @NotNull
    private String username;

    @NotNull
    private String password;
}
