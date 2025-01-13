package com.kadir.modules.salesreport.service.impl;

import com.kadir.common.exception.BaseException;
import com.kadir.common.exception.ErrorMessage;
import com.kadir.common.exception.MessageType;
import com.kadir.common.service.impl.AuthenticationServiceImpl;
import com.kadir.common.utils.pagination.PageableHelper;
import com.kadir.common.utils.pagination.PaginationUtils;
import com.kadir.common.utils.pagination.RestPageableEntity;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

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
    @Scheduled(cron = "0 0 0 * * *")
    public void generateDailySalesReport() {
        Long userId = authenticationService.getCurrentUserId();
        Seller seller = sellerRepository.findByUserId(userId)
                .orElseThrow(() -> new BaseException(
                        new ErrorMessage(MessageType.NO_RECORD_EXIST, "Seller not found")));

        LocalDateTime startDate = LocalDateTime.now().minusDays(1).withHour(0).withMinute(0).withSecond(0).withNano(0);
        LocalDateTime endDate = startDate.plusDays(1).minusSeconds(1);

        BigDecimal totalSales = orderRepository.sumTotalSalesByDate(startDate, endDate);
        Integer totalOrders = orderRepository.countByOrderDateBetween(startDate, endDate);
        Integer totalCustomers = orderRepository.countByCustomerDateBetween(startDate, endDate);

        SalesReport salesReport = new SalesReport();
        salesReport.setReportDate(LocalDateTime.now());
        salesReport.setTotalSales(totalSales);
        salesReport.setTotalOrders(totalOrders);
        salesReport.setTotalCustomers(totalCustomers);
        salesReport.setSeller(seller);
    }

    @Override
    public RestPageableEntity<SalesReportDto> getSalesReports(SalesReportDateRangeDto salesReportDateRangeDto) {
        generateDailySalesReport();
        Pageable pageable = PageableHelper
                .createPageable(salesReportDateRangeDto.getPageNumber(),
                        salesReportDateRangeDto.getPageSize(), salesReportDateRangeDto.getSortBy(), salesReportDateRangeDto.isAsc());
        Page<SalesReport> byReportDateBetween =
                salesReportRepository.findByReportDateBetween(salesReportDateRangeDto.getStartDate(), salesReportDateRangeDto.getEndDate(), pageable);
        RestPageableEntity<SalesReportDto> pageableResponse = PaginationUtils.toPageableResponse(byReportDateBetween,
                SalesReportDto.class, modelMapper);
        pageableResponse.setDocs(byReportDateBetween.getContent().stream()
                .map(product -> modelMapper.map(product, SalesReportDto.class))
                .collect(Collectors.toList()));
        return pageableResponse;
    }
}
