package com.kadir.mapper;

import com.kadir.dto.DtoCartItems;
import com.kadir.dto.DtoCartItemsIU;
import com.kadir.model.CartItems;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CartItemsMapper {

    DtoCartItems mapEntityToDto(CartItems entity);

    CartItems mapDtoToEntity(DtoCartItemsIU dto);

    List<DtoCartItems> mapEntityListToDtoList(List<CartItems> entities);
}
