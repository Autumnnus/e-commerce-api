package com.kadir.modules.order.service;

import com.kadir.common.enums.OrderStatus;
import com.kadir.common.utils.pagination.RestPageableEntity;
import com.kadir.modules.order.dto.OrderDto;
import com.kadir.modules.order.dto.OrderDtoIU;

public interface IOrderService {

    OrderDto createOrder(OrderDtoIU orderDtoIU);

    RestPageableEntity<OrderDto> getAllOrders(int pageNumber, int pageSize);

    RestPageableEntity<OrderDto> getOrdersByUser(Long userId, int pageNumber, int pageSize);

    OrderDto updateOrderStatus(Long orderId, OrderStatus paymentStatus);
}
