package com.kadir.controller.impl;

import com.kadir.controller.IOrderRestController;
import com.kadir.controller.RootEntity;
import com.kadir.dto.DtoOrder;
import com.kadir.dto.DtoOrderIU;
import com.kadir.service.IOrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
