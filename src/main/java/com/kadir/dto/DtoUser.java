package com.kadir.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kadir.enums.UserRole;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DtoUser extends DtoBase {

    @NotNull
    private String username;

    @NotNull
    @JsonIgnore
    private String password;

    @NotNull
    private UserRole role;

    @NotNull
    private String email;

    private String phoneNumber;
}
