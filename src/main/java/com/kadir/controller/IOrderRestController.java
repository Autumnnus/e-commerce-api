package com.kadir.controller;

import com.kadir.dto.DtoOrder;
import com.kadir.dto.DtoOrderIU;

public interface IOrderRestController {

    RootEntity<DtoOrder> createOrder(DtoOrderIU dtoOrderIU);
}
