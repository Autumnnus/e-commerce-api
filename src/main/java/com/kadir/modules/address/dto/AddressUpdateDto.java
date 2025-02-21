package com.kadir.modules.address.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddressUpdateDto {

    private String title;

    private String street;

    private String city;

    private String state;

    private String zipCode;

    private String country;

}
