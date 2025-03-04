package com.kadir.modules.order.controller.impl;

import com.kadir.common.constants.Paths;
import com.kadir.common.controller.ApiResponse;
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
@RequestMapping(Paths.BASE_PATH + "/order")
public class OrderController extends RestBaseController implements IOrderController {

    @Autowired
    private IOrderService orderService;

    @PostMapping
    @Override
    public ApiResponse<OrderDto> createOrder(@RequestBody @Valid OrderDtoIU orderDtoIU) {
        return ok(orderService.createOrder(orderDtoIU));
    }

    @GetMapping
    @Override
    public ApiResponse<RestPageableEntity<OrderDto>> getAllOrders(RestPageableRequest request) {
        return ok(orderService.getAllOrders(request));
    }

    @GetMapping("/user/{id}")
    @Override
    public ApiResponse<RestPageableEntity<OrderDto>> getOrdersByUser(@PathVariable(name = "id") Long customerId, RestPageableRequest request) {
        return ok(orderService.getOrdersByUser(customerId, request));
    }

    @PutMapping("/{id}")
    @Override
    public ApiResponse<OrderDto> updateOrderStatus(
            @PathVariable(name = "id") Long orderId,
            @RequestBody OrderStatusUpdateRequest request) {
        return ok(orderService.updateOrderStatus(orderId, request.getPaymentStatus()));
    }
}
