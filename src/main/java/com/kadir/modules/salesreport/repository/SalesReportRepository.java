package com.kadir.modules.salesreport.repository;

import com.kadir.modules.salesreport.model.SalesReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SalesReportRepository extends JpaRepository<SalesReport, Long> {

    List<SalesReport> findByReportDateBetween(LocalDateTime startDate, LocalDateTime endDate);

    List<SalesReport> findTop10ByOrderByReportDateDesc();
}
