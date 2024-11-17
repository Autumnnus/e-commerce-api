package com.kadir.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DtoCustomer extends DtoBase {

    @NotNull
    private DtoUser user;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;
}
