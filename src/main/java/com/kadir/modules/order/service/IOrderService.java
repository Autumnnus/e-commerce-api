package com.kadir.modules.order.service;

import com.kadir.common.enums.OrderStatus;
import com.kadir.common.utils.pagination.RestPageableEntity;
import com.kadir.modules.order.dto.DtoOrder;
import com.kadir.modules.order.dto.DtoOrderIU;

public interface IOrderService {

    DtoOrder createOrder(DtoOrderIU dtoOrderIU);

    RestPageableEntity<DtoOrder> getAllOrders(int pageNumber, int pageSize);

    RestPageableEntity<DtoOrder> getOrdersByUser(Long userId, int pageNumber, int pageSize);

    DtoOrder updateOrderStatus(Long orderId, OrderStatus paymentStatus);
}
