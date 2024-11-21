package com.kadir.service;

import com.kadir.dto.DtoOrder;
import com.kadir.dto.DtoOrderIU;
import com.kadir.enums.OrderStatus;
import com.kadir.utils.pagination.RestPageableEntity;

public interface IOrderService {

    DtoOrder createOrder(DtoOrderIU dtoOrderIU);

    RestPageableEntity<DtoOrder> getAllOrders(int pageNumber, int pageSize);

    RestPageableEntity<DtoOrder> getOrdersByUser(Long userId, int pageNumber, int pageSize);

    DtoOrder updateOrderStatus(Long orderId, OrderStatus paymentStatus);
}
