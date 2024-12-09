package com.kadir.modules.discount.repository;

import com.kadir.modules.discount.model.Discount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DiscountRepository extends JpaRepository<Discount, Long> {

    Optional<Discount> findByProductId(Long productId);

    Optional<Discount> findByCategoryId(Long categoryId);
}
