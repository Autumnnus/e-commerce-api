package com.kadir.service;

import com.kadir.dto.DtoOrderItems;

import java.util.List;

public interface IOrderItemsService {

    List<DtoOrderItems> getOrderItemsByOrderId(Long orderId);

    List<DtoOrderItems> getAllOrderItems();
}
