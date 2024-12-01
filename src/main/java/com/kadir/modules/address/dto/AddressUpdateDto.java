package com.kadir.modules.address.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddressUpdateDto {

    @NotNull(message = "User id is required")
    private Long userId;

    private String title;

    private String street;

    private String city;

    private String state;

    private String zipCode;

    private String country;

}
