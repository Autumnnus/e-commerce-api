package com.kadir.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DtoOrderItems extends DtoBase {

    private DtoOrder order;

    private DtoProduct product;

    private int quantity;

    private BigDecimal price;

}
