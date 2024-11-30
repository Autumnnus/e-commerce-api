package com.kadir.modules.order.controller.impl;

import com.kadir.common.controller.RootEntity;
import com.kadir.common.controller.impl.RestBaseController;
import com.kadir.common.utils.pagination.RestPageableEntity;
import com.kadir.common.utils.pagination.RestPageableRequest;
import com.kadir.modules.order.controller.IOrderRestController;
import com.kadir.modules.order.dto.DtoOrder;
import com.kadir.modules.order.dto.DtoOrderIU;
import com.kadir.modules.order.dto.OrderStatusUpdateRequest;
import com.kadir.modules.order.service.IOrderService;
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
