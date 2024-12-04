package com.kadir.modules.order.controller.impl;

import com.kadir.common.controller.RootEntity;
import com.kadir.common.controller.impl.RestBaseController;
import com.kadir.common.utils.pagination.RestPageableEntity;
import com.kadir.common.utils.pagination.RestPageableRequest;
import com.kadir.modules.order.controller.IOrderController;
import com.kadir.modules.order.dto.OrderDto;
import com.kadir.modules.order.dto.OrderDtoIU;
import com.kadir.modules.order.dto.OrderStatusUpdateRequest;
import com.kadir.modules.order.service.IOrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rest/api/order")
public class OrderController extends RestBaseController implements IOrderController {

    @Autowired
    private IOrderService orderService;

    @PostMapping
    @Override
    public RootEntity<OrderDto> createOrder(@RequestBody @Valid OrderDtoIU orderDtoIU) {
        return ok(orderService.createOrder(orderDtoIU));
    }

    @GetMapping
    @Override
    public RootEntity<RestPageableEntity<OrderDto>> getAllOrders(RestPageableRequest request) {
        return ok(orderService.getAllOrders(request.getPageNumber(), request.getPageSize()));
    }

    @GetMapping("/user/{id}")
    @Override
    public RootEntity<RestPageableEntity<OrderDto>> getOrdersByUser(@PathVariable(name = "id") Long userId, RestPageableRequest request) {
        return ok(orderService.getOrdersByUser(userId, request.getPageNumber(), request.getPageSize()));
    }

    @PutMapping("/{id}")
    @Override
    public RootEntity<OrderDto> updateOrderStatus(
            @PathVariable(name = "id") Long orderId,
            @RequestBody OrderStatusUpdateRequest request) {
        return ok(orderService.updateOrderStatus(orderId, request.getPaymentStatus()));
    }
}
