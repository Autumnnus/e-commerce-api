package com.kadir.dto;

import com.kadir.model.User;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DtoCustomer extends DtoBase {

    @NotNull
    private User userId;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;
}
