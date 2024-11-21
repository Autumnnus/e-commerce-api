package com.kadir.repository;

import com.kadir.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("SELECT c FROM Order c WHERE c.user.id = ?1")
    Page<Order> findByUserId(Long userId, Pageable pageable);

}
