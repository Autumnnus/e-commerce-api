package com.kadir.modules.address.dto;

import com.kadir.common.dto.DtoBase;
import com.kadir.modules.authentication.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DtoAddress extends DtoBase {

    private User user;

    private String street;

    private String city;

    private String state;

    private String zipCode;

    private String country;
}
