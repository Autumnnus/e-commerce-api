package com.kadir.modules.order.dto;

import com.kadir.common.enums.OrderStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderStatusUpdateRequest {
    private OrderStatus paymentStatus;
}
