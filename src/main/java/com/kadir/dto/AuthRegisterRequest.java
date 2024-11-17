package com.kadir.dto;

import com.kadir.enums.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthRegisterRequest extends AuthRequest {

    @NotNull(message = "Role cannot be null")
    private UserRole role;

    @NotNull(message = "Email cannot be null")
    @Email(message = "Email should be valid")
    private String email;

    @Pattern(regexp = "[0-9\\s]{12}", message = "Phone number should be 12 digits")
    private String phoneNumber;

    private String firstName;

    private String lastName;

    private String companyName;
}
