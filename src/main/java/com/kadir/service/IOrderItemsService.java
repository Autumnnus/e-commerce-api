package com.kadir.service;

import com.kadir.dto.DtoOrderItems;
import com.kadir.utils.pagination.RestPageableEntity;

import java.util.List;

public interface IOrderItemsService {

    List<DtoOrderItems> getOrderItemsByOrderId(Long orderId);

    RestPageableEntity<DtoOrderItems> getAllOrderItems(int pageNumber, int pageSize);
}
