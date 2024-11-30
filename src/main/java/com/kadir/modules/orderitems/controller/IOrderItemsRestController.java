package com.kadir.modules.orderitems.controller;

import com.kadir.common.controller.RootEntity;
import com.kadir.common.utils.pagination.RestPageableEntity;
import com.kadir.common.utils.pagination.RestPageableRequest;
import com.kadir.modules.orderitems.dto.DtoOrderItems;

import java.util.List;

public interface IOrderItemsRestController {

    RootEntity<List<DtoOrderItems>> getOrderItemsByOrderId(Long orderId);

    RootEntity<RestPageableEntity<DtoOrderItems>> getAllOrderItems(RestPageableRequest request);
}
