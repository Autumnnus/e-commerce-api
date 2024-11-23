package com.kadir.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DtoOrderItems extends DtoBase {

    private DtoOrder order;

    private DtoProduct product;

    private int quantity;

    private BigDecimal price;

}
