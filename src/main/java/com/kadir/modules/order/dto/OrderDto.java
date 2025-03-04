package com.kadir.modules.order.dto;

import com.kadir.common.dto.BaseDto;
import com.kadir.common.enums.OrderStatus;
import com.kadir.modules.authentication.dto.CustomerDto;
import com.kadir.modules.orderitems.dto.OrderItemsDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto extends BaseDto {

    private CustomerDto customer;

    private Set<OrderItemsDto> orderItems;

    private LocalDateTime orderDate = LocalDateTime.now();

    private BigDecimal totalAmount;

    private String paymentMethod;

    private OrderStatus paymentStatus;
}
