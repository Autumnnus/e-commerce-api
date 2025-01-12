package com.kadir.modules.orderitems.controller.impl;

import com.kadir.common.constants.Paths;
import com.kadir.common.controller.ApiResponse;
import com.kadir.common.controller.impl.RestBaseController;
import com.kadir.common.utils.pagination.RestPageableEntity;
import com.kadir.common.utils.pagination.RestPageableRequest;
import com.kadir.modules.orderitems.controller.IOrderItemsController;
import com.kadir.modules.orderitems.dto.OrderItemsDto;
import com.kadir.modules.orderitems.service.IOrderItemsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(Paths.BASE_PATH + "/order-items")
public class OrderItemsController extends RestBaseController implements IOrderItemsController {

    @Autowired
    private IOrderItemsService orderItemsService;

    @GetMapping("/{id}")
    @Override
    public ApiResponse<List<OrderItemsDto>> getOrderItemsByOrderId(@PathVariable(name = "id") Long orderId) {
        return ok(orderItemsService.getOrderItemsByOrderId(orderId));
    }

    @GetMapping
    @Override
    public ApiResponse<RestPageableEntity<OrderItemsDto>> getAllOrderItems(RestPageableRequest request) {
        return ok(orderItemsService.getAllOrderItems(request));
    }
}
