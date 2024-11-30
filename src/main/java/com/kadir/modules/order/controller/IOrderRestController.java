package com.kadir.modules.order.controller;

import com.kadir.common.controller.RootEntity;
import com.kadir.common.utils.pagination.RestPageableEntity;
import com.kadir.common.utils.pagination.RestPageableRequest;
import com.kadir.modules.order.dto.DtoOrder;
import com.kadir.modules.order.dto.DtoOrderIU;
import com.kadir.modules.order.dto.OrderStatusUpdateRequest;

public interface IOrderRestController {

    RootEntity<DtoOrder> createOrder(DtoOrderIU dtoOrderIU);

    RootEntity<RestPageableEntity<DtoOrder>> getAllOrders(RestPageableRequest request);

    RootEntity<RestPageableEntity<DtoOrder>> getOrdersByUser(Long userId, RestPageableRequest request);

    RootEntity<DtoOrder> updateOrderStatus(Long orderId, OrderStatusUpdateRequest request);
}
