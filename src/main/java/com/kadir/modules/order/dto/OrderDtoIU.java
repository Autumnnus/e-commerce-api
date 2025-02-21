package com.kadir.modules.order.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderDtoIU {

    private String paymentMethod;

    private String paymentStatus;
}
