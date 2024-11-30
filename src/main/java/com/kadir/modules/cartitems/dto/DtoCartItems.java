package com.kadir.modules.cartitems.dto;

import com.kadir.common.dto.DtoBase;
import com.kadir.modules.authentication.dto.DtoUser;
import com.kadir.modules.product.dto.DtoProductIU;
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

    private DtoProductIU product;

    private int quantity;

}
