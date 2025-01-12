package com.kadir.modules.salesreport.dto;

import com.kadir.common.dto.BaseDto;
import com.kadir.modules.authentication.dto.SellerDto;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class SalesReportDto extends BaseDto {

    private LocalDateTime reportDate;

    private BigDecimal totalSales;

    private Integer totalOrders;

    private Integer totalCustomers;

    private SellerDto seller;
}
