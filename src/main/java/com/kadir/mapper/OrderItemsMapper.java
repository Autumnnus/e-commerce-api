package com.kadir.mapper;

import com.kadir.dto.DtoOrderItems;
import com.kadir.model.OrderItems;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderItemsMapper {

    List<DtoOrderItems> mapEntityListToDtoList(List<OrderItems> entities);

    List<OrderItems> mapDtoListToEntityList(List<DtoOrderItems> dtos);
}
