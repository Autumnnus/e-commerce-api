package com.kadir.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class DtoOrderItems extends DtoBase {

    private DtoOrder order;

    private DtoProduct product;

    private int quantity;

    private BigDecimal price;

}
