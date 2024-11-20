package com.kadir.service;

import com.kadir.dto.DtoOrder;
import com.kadir.dto.DtoOrderIU;

import java.util.List;

public interface IOrderService {

    DtoOrder createOrder(DtoOrderIU dtoOrderIU);

    List<DtoOrder> getAllOrders();

    List<DtoOrder> getOrdersByUser(String userId);

    DtoOrder updateOrderStatus(String orderId, String status);
}
