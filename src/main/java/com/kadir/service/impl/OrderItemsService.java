package com.kadir.service.impl;

import com.kadir.dto.DtoOrderItems;
import com.kadir.service.IOrderItemsService;

import java.util.List;

public class OrderItemsService implements IOrderItemsService {
    @Override
    public List<DtoOrderItems> getOrderItemsByOrderId(Long orderId) {
        return List.of();
    }

    @Override
    public List<DtoOrderItems> getAllOrderItems() {
        return List.of();
    }
}
