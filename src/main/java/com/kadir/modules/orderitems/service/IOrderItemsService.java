package com.kadir.modules.orderitems.service;

import com.kadir.common.utils.pagination.RestPageableEntity;
import com.kadir.modules.orderitems.dto.OrderItemsDto;

import java.util.List;

public interface IOrderItemsService {

    List<OrderItemsDto> getOrderItemsByOrderId(Long orderId);

    RestPageableEntity<OrderItemsDto> getAllOrderItems(int pageNumber, int pageSize);
}
