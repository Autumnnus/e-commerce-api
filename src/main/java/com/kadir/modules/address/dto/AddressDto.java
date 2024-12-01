package com.kadir.modules.address.dto;

import com.kadir.common.dto.DtoBase;
import com.kadir.modules.authentication.dto.UserDtoIU;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddressDto extends DtoBase {

    private UserDtoIU user;

    private String title;

    private String street;

    private String city;

    private String state;

    private String zipCode;

    private String country;

//    private boolean isDefault;
}
