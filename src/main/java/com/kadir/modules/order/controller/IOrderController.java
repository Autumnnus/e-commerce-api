package com.kadir.modules.order.controller;

import com.kadir.common.controller.RootEntity;
import com.kadir.common.utils.pagination.RestPageableEntity;
import com.kadir.common.utils.pagination.RestPageableRequest;
import com.kadir.modules.order.dto.OrderDto;
import com.kadir.modules.order.dto.OrderDtoIU;
import com.kadir.modules.order.dto.OrderStatusUpdateRequest;

public interface IOrderController {

    RootEntity<OrderDto> createOrder(OrderDtoIU orderDtoIU);

    RootEntity<RestPageableEntity<OrderDto>> getAllOrders(RestPageableRequest request);

    RootEntity<RestPageableEntity<OrderDto>> getOrdersByUser(Long userId, RestPageableRequest request);

    RootEntity<OrderDto> updateOrderStatus(Long orderId, OrderStatusUpdateRequest request);
}
