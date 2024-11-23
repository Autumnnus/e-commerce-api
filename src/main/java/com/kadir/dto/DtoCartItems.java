package com.kadir.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DtoCartItems extends DtoBase {

    private DtoUser user;

    private DtoProduct product;

    private int quantity;

}
