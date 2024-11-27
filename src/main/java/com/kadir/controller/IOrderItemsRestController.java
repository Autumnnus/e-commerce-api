package com.kadir.controller;

import com.kadir.dto.DtoOrderItems;
import com.kadir.utils.pagination.RestPageableEntity;
import com.kadir.utils.pagination.RestPageableRequest;

import java.util.List;

public interface IOrderItemsRestController {

    RootEntity<List<DtoOrderItems>> getOrderItemsByOrderId(Long orderId);

    RootEntity<RestPageableEntity<DtoOrderItems>> getAllOrderItems(RestPageableRequest request);
}
