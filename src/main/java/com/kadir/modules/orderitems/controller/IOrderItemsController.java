package com.kadir.modules.orderitems.controller;

import com.kadir.common.controller.ApiResponse;
import com.kadir.common.utils.pagination.RestPageableEntity;
import com.kadir.common.utils.pagination.RestPageableRequest;
import com.kadir.modules.orderitems.dto.OrderItemsDto;

import java.util.List;

public interface IOrderItemsController {

    ApiResponse<List<OrderItemsDto>> getOrderItemsByOrderId(Long orderId);

    ApiResponse<RestPageableEntity<OrderItemsDto>> getAllOrderItems(RestPageableRequest request);
}
