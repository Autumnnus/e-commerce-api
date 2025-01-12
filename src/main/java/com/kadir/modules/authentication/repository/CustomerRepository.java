package com.kadir.modules.authentication.repository;

import com.kadir.modules.authentication.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Query("SELECT c FROM Customer c WHERE c.user.id = ?1")
    Optional<Customer> findByUserId(Long userId);

    Integer countByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate);
}
