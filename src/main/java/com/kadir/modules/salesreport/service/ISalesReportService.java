package com.kadir.modules.salesreport.service;

import com.kadir.common.utils.pagination.RestPageableEntity;
import com.kadir.modules.salesreport.dto.SalesReportDateRangeDto;
import com.kadir.modules.salesreport.dto.SalesReportDto;

public interface ISalesReportService {

    RestPageableEntity<SalesReportDto> getSalesReports(SalesReportDateRangeDto salesReportDateRangeDto);
}
