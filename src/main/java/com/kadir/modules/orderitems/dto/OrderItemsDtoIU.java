package com.kadir.modules.orderitems.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderItemsDtoIU {

    private Long orderId;

    private Long productId;

    private int quantity;

    private BigDecimal price;
}
