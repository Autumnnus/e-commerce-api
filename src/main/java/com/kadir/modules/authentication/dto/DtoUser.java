package com.kadir.modules.authentication.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kadir.common.dto.DtoBase;
import com.kadir.common.enums.UserRole;
import com.kadir.modules.address.model.Address;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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

    private Set<Address> address;
}
