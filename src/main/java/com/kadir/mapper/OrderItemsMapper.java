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

    Order mapDtoToEntity(DtoOrderIU dto);

    DtoOrderItems mapEntityToDto(OrderItems entity);

    OrderItems mapDtoToEntity(DtoOrderItems dto);

    List<DtoOrderItems> mapEntityListToDtoList(List<OrderItems> entities);

    List<OrderItems> mapDtoListToEntityList(List<DtoOrderItems> dtos);
}
