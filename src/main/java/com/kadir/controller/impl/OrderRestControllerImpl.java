package com.kadir.controller.impl;

import com.kadir.controller.IOrderRestController;
import com.kadir.controller.RootEntity;
import com.kadir.dto.DtoOrder;
import com.kadir.dto.DtoOrderIU;
import com.kadir.dto.OrderStatusUpdateRequest;
import com.kadir.service.IOrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/api/order")
public class OrderRestControllerImpl extends RestBaseController implements IOrderRestController {

    @Autowired
    private IOrderService orderService;

    @PostMapping("/create")
    @Override
    public RootEntity<DtoOrder> createOrder(@RequestBody @Valid DtoOrderIU dtoOrderIU) {
        return ok(orderService.createOrder(dtoOrderIU));
    }

    @GetMapping("/all")
    @Override
    public RootEntity<List<DtoOrder>> getAllOrders() {
        return ok(orderService.getAllOrders());
    }

    @GetMapping("/user/{id}")
    @Override
    public RootEntity<List<DtoOrder>> getOrdersByUser(@PathVariable(name = "id") Long userId) {
        return ok(orderService.getOrdersByUser(userId));
    }

    @PutMapping("/update-status/{id}")
    @Override
    public RootEntity<DtoOrder> updateOrderStatus(
            @PathVariable(name = "id") Long orderId,
            @RequestBody OrderStatusUpdateRequest request) {
        return ok(orderService.updateOrderStatus(orderId, request.getPaymentStatus()));
    }
}
