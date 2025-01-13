package com.kadir.modules.salesreport.controller;

import com.kadir.common.controller.ApiResponse;
import com.kadir.common.utils.pagination.RestPageableEntity;
import com.kadir.modules.salesreport.dto.SalesReportDateRangeDto;
import com.kadir.modules.salesreport.dto.SalesReportDto;

public interface ISalesReportController {

    ApiResponse<RestPageableEntity<SalesReportDto>> getSalesReports(SalesReportDateRangeDto salesReportDateRangeDto);
}
