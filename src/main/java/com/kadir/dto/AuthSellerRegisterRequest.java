package com.kadir.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthSellerRegisterRequest extends AuthRegisterRequest {


    @NotNull(message = "Company name is required")
    private String companyName;

}
