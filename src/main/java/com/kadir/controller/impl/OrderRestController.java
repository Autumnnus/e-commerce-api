package com.kadir.controller.impl;

import com.kadir.controller.IOrderRestController;
import com.kadir.controller.RootEntity;
import com.kadir.dto.DtoOrder;
import com.kadir.dto.DtoOrderIU;
import com.kadir.dto.OrderStatusUpdateRequest;
import com.kadir.service.IOrderService;
import com.kadir.utils.pagination.RestPageableEntity;
import com.kadir.utils.pagination.RestPageableRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rest/api/order")
public class OrderRestController extends RestBaseController implements IOrderRestController {

    @Autowired
    private IOrderService orderService;

    @PostMapping("/create")
    @Override
    public RootEntity<DtoOrder> createOrder(@RequestBody @Valid DtoOrderIU dtoOrderIU) {
        return ok(orderService.createOrder(dtoOrderIU));
    }

    @GetMapping("/all")
    @Override
    public RootEntity<RestPageableEntity<DtoOrder>> getAllOrders(RestPageableRequest request) {
        return ok(orderService.getAllOrders(request.getPageNumber(), request.getPageSize()));
    }

    @GetMapping("/user/{id}")
    @Override
    public RootEntity<RestPageableEntity<DtoOrder>> getOrdersByUser(@PathVariable(name = "id") Long userId, RestPageableRequest request) {
        return ok(orderService.getOrdersByUser(userId, request.getPageNumber(), request.getPageSize()));
    }

    @PutMapping("/update-status/{id}")
    @Override
    public RootEntity<DtoOrder> updateOrderStatus(
            @PathVariable(name = "id") Long orderId,
            @RequestBody OrderStatusUpdateRequest request) {
        return ok(orderService.updateOrderStatus(orderId, request.getPaymentStatus()));
    }
}
