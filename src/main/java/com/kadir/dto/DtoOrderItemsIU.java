package com.kadir.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class DtoOrderItemsIU {

    private Long orderId;

    private Long productId;

    private int quantity;

    private BigDecimal price;
}
