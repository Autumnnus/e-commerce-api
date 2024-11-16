package com.kadir.dto;

import com.kadir.enums.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthRequest {

    @NotNull(message = "Username cannot be null")
    private String username;

    @NotNull(message = "Password be null")
    @Size(min = 8, message = "Password should be at least 8 characters")
    private String password;

    @NotNull(message = "Role cannot be null")
    private UserRole role;

    @NotNull(message = "Email cannot be null")
    @Email(message = "Email should be valid")
    private String email;

}
