package com.kadir.repository;

import com.kadir.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("SELECT c FROM Order c WHERE c.user.id = ?1")
    List<Order> findByUserId(Long userId);
    
}
