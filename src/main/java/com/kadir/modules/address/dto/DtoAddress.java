package com.kadir.modules.address.dto;

import com.kadir.common.dto.DtoBase;
import com.kadir.modules.authentication.dto.DtoUserIU;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DtoAddress extends DtoBase {

    private DtoUserIU user;

    private String title;

    private String street;

    private String city;

    private String state;

    private String zipCode;

    private String country;

//    private boolean isDefault;
}
