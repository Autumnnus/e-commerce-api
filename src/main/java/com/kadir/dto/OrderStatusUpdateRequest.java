package com.kadir.dto;

import com.kadir.enums.OrderStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderStatusUpdateRequest {
    private OrderStatus paymentStatus;
}
