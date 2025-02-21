package com.kadir.modules.order.controller;

import com.kadir.common.controller.ApiResponse;
import com.kadir.common.utils.pagination.RestPageableEntity;
import com.kadir.common.utils.pagination.RestPageableRequest;
import com.kadir.modules.order.dto.OrderDto;
import com.kadir.modules.order.dto.OrderDtoIU;
import com.kadir.modules.order.dto.OrderStatusUpdateRequest;

public interface IOrderController {

    ApiResponse<OrderDto> createOrder(OrderDtoIU orderDtoIU);

    ApiResponse<RestPageableEntity<OrderDto>> getAllOrders(RestPageableRequest request);

    ApiResponse<RestPageableEntity<OrderDto>> getOrdersByUser(Long customerId, RestPageableRequest request);

    ApiResponse<OrderDto> updateOrderStatus(Long orderId, OrderStatusUpdateRequest request);
}
