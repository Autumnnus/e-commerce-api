package com.kadir.modules.authentication.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kadir.common.dto.BaseDto;
import com.kadir.common.enums.UserRole;
import com.kadir.modules.address.dto.AddressCreateDto;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto extends BaseDto {

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

    private AddressCreateDto address;
}
