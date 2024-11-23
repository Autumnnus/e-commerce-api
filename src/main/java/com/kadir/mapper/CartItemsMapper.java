package com.kadir.mapper;

import com.kadir.dto.DtoCartItems;
import com.kadir.dto.DtoCartItemsIU;
import com.kadir.model.CartItems;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CartItemsMapper {

    DtoCartItems mapEntityToDto(CartItems entity);

//    DtoCartItems mapEntityToDto(CartItems entity, List<OrderItems> orderItems);

    //    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
//    @Mapping(target = "updatedAt", expression = "java(java.time.LocalDateTime.now())")
//    @Mapping(target = "product.id", source = "productId")
//    @Mapping(target = "quantity", source = "quantity")
//    @Mapping(target = "user.id", source = "userId")
    CartItems mapDtoToEntity(DtoCartItemsIU dto);

//    DtoOrderItems mapEntityToDto(OrderItems entity);

//    OrderItems mapDtoToEntity(DtoOrderItems dto);

    List<DtoCartItems> mapEntityListToDtoList(List<CartItems> entities);
}
