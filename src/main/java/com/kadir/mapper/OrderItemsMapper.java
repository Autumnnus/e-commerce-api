package com.kadir.mapper;

import com.kadir.dto.DtoOrderItems;
import com.kadir.dto.DtoOrderItemsIU;
import com.kadir.model.OrderItems;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderItemsMapper {

    DtoOrderItems mapEntityToDto(OrderItems entity);

    OrderItems mapDtoToEntity(DtoOrderItemsIU dto);

    List<DtoOrderItems> mapEntityListToDtoList(List<OrderItems> entities);
}
