package com.kadir.service;

import com.kadir.dto.DtoOrder;
import com.kadir.dto.DtoOrderIU;
import com.kadir.enums.OrderStatus;

import java.util.List;

public interface IOrderService {

    DtoOrder createOrder(DtoOrderIU dtoOrderIU);

    List<DtoOrder> getAllOrders();

    List<DtoOrder> getOrdersByUser(Long userId);

    DtoOrder updateOrderStatus(Long orderId, OrderStatus paymentStatus);
}
