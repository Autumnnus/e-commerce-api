package com.kadir.dto;

import com.kadir.enums.OrderStatus;
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
public class DtoOrder extends DtoBase {

    private DtoUser user;

    private Set<DtoOrderItems> orderItems;

    private LocalDateTime orderDate = LocalDateTime.now();

    private BigDecimal totalAmount;

    private String paymentMethod;

    private OrderStatus paymentStatus;
}
