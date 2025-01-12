package com.kadir.modules.salesreport.service.impl;

import com.kadir.common.exception.BaseException;
import com.kadir.common.exception.ErrorMessage;
import com.kadir.common.exception.MessageType;
import com.kadir.common.service.impl.AuthenticationServiceImpl;
import com.kadir.modules.authentication.model.Seller;
import com.kadir.modules.authentication.repository.CustomerRepository;
import com.kadir.modules.authentication.repository.SellerRepository;
import com.kadir.modules.order.repository.OrderRepository;
import com.kadir.modules.salesreport.dto.SalesReportDateRangeDto;
import com.kadir.modules.salesreport.dto.SalesReportDto;
import com.kadir.modules.salesreport.model.SalesReport;
import com.kadir.modules.salesreport.repository.SalesReportRepository;
import com.kadir.modules.salesreport.service.ISalesReportService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SalesReportService implements ISalesReportService {

    private final SalesReportRepository salesReportRepository;
    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;
    private final CustomerRepository customerRepository;
    private final AuthenticationServiceImpl authenticationService;
    private final SellerRepository sellerRepository;

    @Transactional
    @Override
    public SalesReportDto generateDailySalesReport(SalesReportDateRangeDto salesReportDateRangeDto) {
        Long userId = authenticationService.getCurrentUserId();
        Seller seller = sellerRepository.findByUserId(userId)
                .orElseThrow(() -> new BaseException(
                        new ErrorMessage(MessageType.NO_RECORD_EXIST, "Seller not found")));

        LocalDateTime startDate = salesReportDateRangeDto.getStartDate();
        LocalDateTime endDate = salesReportDateRangeDto.getEndDate();

        BigDecimal totalSales = orderRepository.sumTotalSalesByDate(startDate, endDate);
        Integer totalOrders = orderRepository.countByOrderDateBetween(startDate, endDate);
        Integer totalCustomers = customerRepository.countByCreatedAtBetween(startDate, endDate);

        SalesReport salesReport = new SalesReport();
        salesReport.setReportDate(LocalDateTime.now());
        salesReport.setTotalSales(totalSales);
        salesReport.setTotalOrders(totalOrders);
        salesReport.setTotalCustomers(totalCustomers);
        salesReport.setSeller(seller);

        return modelMapper.map(salesReportRepository.save(salesReport), SalesReportDto.class);
    }

    @Override
    public List<SalesReportDto> getSalesReports(SalesReportDateRangeDto salesReportDateRangeDto) {
        List<SalesReport> byReportDateBetween =
                salesReportRepository.findByReportDateBetween(salesReportDateRangeDto.getStartDate(), salesReportDateRangeDto.getEndDate());
        return modelMapper.map(byReportDateBetween, List.class);
    }

    @Override
    public List<SalesReportDto> getLatestSalesReports() {
        List<SalesReport> top10ByOrderByReportDateDesc = salesReportRepository.findTop10ByOrderByReportDateDesc();
        return modelMapper.map(top10ByOrderByReportDateDesc, List.class);
    }
}
