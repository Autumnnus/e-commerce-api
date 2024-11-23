package com.kadir.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DtoCustomer extends DtoBase {

    @NotNull
    private DtoUser user;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;
}
