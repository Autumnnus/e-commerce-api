package com.kadir.modules.salesreport.dto;

import com.kadir.common.utils.pagination.RestPageableRequest;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class SalesReportDateRangeDto extends RestPageableRequest {

    @NotNull(message = "Start Date is required")
    private LocalDateTime startDate;

    private LocalDateTime endDate = LocalDateTime.now();
}
