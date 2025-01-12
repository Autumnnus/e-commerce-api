package com.kadir.modules.salesreport.controller;

import com.kadir.common.controller.ApiResponse;
import com.kadir.modules.salesreport.dto.SalesReportDateRangeDto;
import com.kadir.modules.salesreport.dto.SalesReportDto;

import java.util.List;

public interface ISalesReportController {

    ApiResponse<SalesReportDto> generateDailySalesReport(SalesReportDateRangeDto salesReportDateRangeDto);

    ApiResponse<List<SalesReportDto>> getSalesReports(SalesReportDateRangeDto salesReportDateRangeDto);

    ApiResponse<List<SalesReportDto>> getLatestSalesReports();
}
