package com.kadir.modules.salesreport.service;

import com.kadir.modules.salesreport.dto.SalesReportDateRangeDto;
import com.kadir.modules.salesreport.dto.SalesReportDto;

import java.util.List;

public interface ISalesReportService {

    SalesReportDto generateDailySalesReport(SalesReportDateRangeDto salesReportDateRangeDto);

    List<SalesReportDto> getSalesReports(SalesReportDateRangeDto salesReportDateRangeDto);

    List<SalesReportDto> getLatestSalesReports();
}
