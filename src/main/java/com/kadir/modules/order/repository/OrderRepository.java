package com.kadir.modules.order.repository;

import com.kadir.modules.order.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("SELECT c FROM Order c WHERE c.customer.id = ?1")
    Page<Order> findByCustomerId(Long customerId, Pageable pageable);

//    @Query("SELECT SUM(o.totalAmount) FROM Order o WHERE o.orderDate = :date")
//    BigDecimal sumTotalSalesByDate(LocalDateTime date);

    @Query("SELECT SUM(o.totalAmount) FROM Order o WHERE o.orderDate BETWEEN :startDate AND :endDate")
    BigDecimal sumTotalSalesByDate(LocalDateTime startDate, LocalDateTime endDate);

    Integer countByOrderDateBetween(LocalDateTime startDate, LocalDateTime endDate);
}