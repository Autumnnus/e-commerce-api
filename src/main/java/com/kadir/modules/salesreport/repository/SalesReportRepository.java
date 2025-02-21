package com.kadir.modules.salesreport.repository;

import com.kadir.modules.salesreport.model.SalesReport;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface SalesReportRepository extends JpaRepository<SalesReport, Long> {

    Page<SalesReport> findByReportDateBetween(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);
}
