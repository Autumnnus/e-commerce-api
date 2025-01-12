package com.kadir.modules.order.service;

import com.kadir.common.enums.OrderStatus;
import com.kadir.common.utils.pagination.RestPageableEntity;
import com.kadir.common.utils.pagination.RestPageableRequest;
import com.kadir.modules.order.dto.OrderDto;
import com.kadir.modules.order.dto.OrderDtoIU;

public interface IOrderService {

    OrderDto createOrder(OrderDtoIU orderDtoIU);

    RestPageableEntity<OrderDto> getAllOrders(RestPageableRequest request);

    RestPageableEntity<OrderDto> getOrdersByUser(Long customerId, RestPageableRequest request);

    OrderDto updateOrderStatus(Long orderId, OrderStatus paymentStatus);
}
