package com.kadir.controller.impl;

import com.kadir.controller.IOrderItemsRestController;
import com.kadir.controller.RootEntity;
import com.kadir.dto.DtoOrderItems;
import com.kadir.service.IOrderItemsService;
import com.kadir.utils.pagination.RestPageableEntity;
import com.kadir.utils.pagination.RestPageableRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/rest/api/order-items")
public class OrderItemsRestController extends RestBaseController implements IOrderItemsRestController {

    @Autowired
    private IOrderItemsService orderItemsService;

    @GetMapping("/{orderId}")
    @Override
    public RootEntity<List<DtoOrderItems>> getOrderItemsByOrderId(@PathVariable(name = "orderId") Long orderId) {
        return ok(orderItemsService.getOrderItemsByOrderId(orderId));
    }

    @GetMapping("/all")
    @Override
    public RootEntity<RestPageableEntity<DtoOrderItems>> getAllOrderItems(RestPageableRequest request) {
        return ok(orderItemsService.getAllOrderItems(request.getPageNumber(), request.getPageSize()));
    }
}
