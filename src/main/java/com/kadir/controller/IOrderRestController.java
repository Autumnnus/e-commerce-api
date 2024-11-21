package com.kadir.controller;

import com.kadir.dto.DtoOrder;
import com.kadir.dto.DtoOrderIU;
import com.kadir.dto.OrderStatusUpdateRequest;

import java.util.List;

public interface IOrderRestController {

    RootEntity<DtoOrder> createOrder(DtoOrderIU dtoOrderIU);

    RootEntity<List<DtoOrder>> getAllOrders();

    RootEntity<List<DtoOrder>> getOrdersByUser(Long userId);

    RootEntity<DtoOrder> updateOrderStatus(Long orderId, OrderStatusUpdateRequest request);
}
