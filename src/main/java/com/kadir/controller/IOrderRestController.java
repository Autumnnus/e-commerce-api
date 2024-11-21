package com.kadir.controller;

import com.kadir.dto.DtoOrder;
import com.kadir.dto.DtoOrderIU;
import com.kadir.dto.OrderStatusUpdateRequest;
import com.kadir.utils.pagination.RestPageableEntity;
import com.kadir.utils.pagination.RestPageableRequest;

public interface IOrderRestController {

    RootEntity<DtoOrder> createOrder(DtoOrderIU dtoOrderIU);

    RootEntity<RestPageableEntity<DtoOrder>> getAllOrders(RestPageableRequest request);

    RootEntity<RestPageableEntity<DtoOrder>> getOrdersByUser(Long userId, RestPageableRequest request);

    RootEntity<DtoOrder> updateOrderStatus(Long orderId, OrderStatusUpdateRequest request);
}
