package com.kadir.modules.salesreport.model;

import com.kadir.common.model.BaseEntity;
import com.kadir.modules.authentication.model.Seller;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "sales_report")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SalesReport extends BaseEntity {

    @Column(name = "report_date")
    private LocalDateTime reportDate;

    @Column(name = "total_sales")
    private BigDecimal totalSales;

    @Column(name = "total_orders")
    private Integer totalOrders;

    @Column(name = "total_customers")
    private Integer totalCustomers;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "seller_id", nullable = false)
    private Seller seller;
}
