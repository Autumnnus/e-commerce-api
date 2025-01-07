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
public class AddressCreateDto {

    @NotNull(message = "Title is required")
    private String title;

    @NotNull(message = "Street is required")
    private String street;

    @NotNull(message = "City is required")
    private String city;

    private String state;

    @NotNull(message = "Zip code is required")
    private String zipCode;

    @NotNull(message = "Country is required")
    private String country;

}
