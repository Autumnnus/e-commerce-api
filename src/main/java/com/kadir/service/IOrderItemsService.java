package com.kadir.service;

import com.kadir.dto.DtoOrderItems;

import java.util.List;

public interface IOrderItemsService {

    List<DtoOrderItems> getOrderItemsByOrderId(String orderId);

    List<DtoOrderItems> getAllOrderItems();
}
