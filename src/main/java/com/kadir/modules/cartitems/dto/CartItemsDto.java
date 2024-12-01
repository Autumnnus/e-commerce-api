package com.kadir.modules.cartitems.dto;

import com.kadir.common.dto.DtoBase;
import com.kadir.modules.authentication.dto.UserDto;
import com.kadir.modules.product.dto.ProductDtoIU;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartItemsDto extends DtoBase {

    private UserDto user;

    private ProductDtoIU product;

    private int quantity;

}
