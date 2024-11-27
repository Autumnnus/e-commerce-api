package com.kadir.mapper;

import com.kadir.dto.DtoCartItems;
import com.kadir.dto.DtoCartItemsIU;
import com.kadir.model.CartItems;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CartItemsMapper {

    DtoCartItems mapEntityToDto(CartItems entity);

    @Mapping(target = "user.id", source = "userId")
    @Mapping(target = "product.id", source = "productId")
    CartItems mapDtoToEntity(DtoCartItemsIU dto);

    List<DtoCartItems> mapEntityListToDtoList(List<CartItems> entities);
}
