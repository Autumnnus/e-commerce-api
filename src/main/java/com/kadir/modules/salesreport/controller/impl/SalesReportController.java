package com.kadir.modules.salesreport.controller.impl;

import com.kadir.common.constants.Paths;
import com.kadir.common.controller.ApiResponse;
import com.kadir.common.controller.impl.RestBaseController;
import com.kadir.modules.salesreport.controller.ISalesReportController;
import com.kadir.modules.salesreport.dto.SalesReportDateRangeDto;
import com.kadir.modules.salesreport.dto.SalesReportDto;
import com.kadir.modules.salesreport.service.ISalesReportService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(Paths.BASE_PATH + "/sales-report")
public class SalesReportController extends RestBaseController implements ISalesReportController {

    @Autowired
    private ISalesReportService salesReportService;

    @PreAuthorize("hasRole('SELLER')")
    @GetMapping("/daily")
    @Override
    public ApiResponse<SalesReportDto> generateDailySalesReport(@RequestBody @Valid SalesReportDateRangeDto salesReportDateRangeDto) {
        return ok(salesReportService.generateDailySalesReport(salesReportDateRangeDto));
    }

    @PreAuthorize("hasRole('SELLER')")
    @GetMapping
    @Override
    public ApiResponse<List<SalesReportDto>> getSalesReports(@RequestBody @Valid SalesReportDateRangeDto salesReportDateRangeDto) {
        return ok(salesReportService.getSalesReports(salesReportDateRangeDto));
    }

    @PreAuthorize("hasRole('SELLER')")
    @GetMapping("/latest")
    @Override
    public ApiResponse<List<SalesReportDto>> getLatestSalesReports() {
        return ok(salesReportService.getLatestSalesReports());
    }
}
