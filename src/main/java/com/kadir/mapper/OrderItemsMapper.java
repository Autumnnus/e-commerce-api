package com.kadir.mapper;

import com.kadir.dto.DtoOrder;
import com.kadir.dto.DtoOrderIU;
import com.kadir.dto.DtoOrderItems;
import com.kadir.model.Order;
import com.kadir.model.OrderItems;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderItemsMapper {

    DtoOrder mapEntityToDto(Order entity);

    // DTO -> Entity dönüşümü
    Order mapDtoToEntity(DtoOrderIU dto);

    // OrderItems -> DtoOrderItems dönüşümü
    DtoOrderItems mapEntityToDto(OrderItems entity);

    // DtoOrderItems -> OrderItems dönüşümü
    OrderItems mapDtoToEntity(DtoOrderItems dto);

    // Liste dönüşümleri
    List<DtoOrderItems> mapEntityListToDtoList(List<OrderItems> entities);

    List<OrderItems> mapDtoListToEntityList(List<DtoOrderItems> dtos);
}
