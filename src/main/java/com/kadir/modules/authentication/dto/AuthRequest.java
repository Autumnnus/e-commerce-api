package com.kadir.modules.authentication.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthRequest {

    @NotNull(message = "Username cannot be null")
    @Size(min = 3, message = "Username should be at least 3 characters")
    private String username;

    @NotNull(message = "Password be null")
    @Size(min = 8, message = "Password should be at least 8 characters")
    private String password;

}
