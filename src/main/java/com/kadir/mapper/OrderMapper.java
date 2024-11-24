package com.kadir.mapper;

import com.kadir.dto.DtoOrder;
import com.kadir.dto.DtoOrderIU;
import com.kadir.dto.DtoOrderItems;
import com.kadir.model.Order;
import com.kadir.model.OrderItems;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    @Mapping(target = "orderItems", ignore = true)
    DtoOrder mapEntityToDto(Order entity);

    DtoOrder mapEntityToDto(Order entity, List<OrderItems> orderItems);

    Order mapDtoToEntity(DtoOrderIU dto);

    DtoOrderItems mapEntityToDto(OrderItems entity);

    OrderItems mapDtoToEntity(DtoOrderItems dto);

}