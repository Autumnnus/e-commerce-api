package com.kadir.modules.orderitems.dto;

import com.kadir.common.dto.DtoBase;
import com.kadir.modules.order.dto.OrderDtoIU;
import com.kadir.modules.product.dto.ProductDtoIU;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemsDto extends DtoBase {

    private OrderDtoIU order;

    private ProductDtoIU product;

    private int quantity;

    private BigDecimal price;

}
