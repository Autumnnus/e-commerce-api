package com.kadir.modules.orderitems.service;

import com.kadir.common.utils.pagination.RestPageableEntity;
import com.kadir.modules.orderitems.dto.DtoOrderItems;

import java.util.List;

public interface IOrderItemsService {

    List<DtoOrderItems> getOrderItemsByOrderId(Long orderId);

    RestPageableEntity<DtoOrderItems> getAllOrderItems(int pageNumber, int pageSize);
}
