package com.kadir.modules.authentication.dto;

import com.kadir.common.enums.UserRole;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DtoUserIU {

    @NotNull
    private String username;

    @NotNull
    private UserRole role;

    @NotNull
    private String email;

    private String phoneNumber;

    private String address;
}
