package com.kadir.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DtoCartItems extends DtoBase {

    private DtoUser user;

    private DtoProduct product;

    private int quantity;

}
