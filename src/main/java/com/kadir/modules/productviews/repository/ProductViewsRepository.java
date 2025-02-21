package com.kadir.modules.productviews.repository;

import com.kadir.modules.productviews.model.ProductViews;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductViewsRepository extends JpaRepository<ProductViews, Long> {

    @Query("SELECT p FROM ProductViews p WHERE p.product.id = :productId AND " +
            "(p.user.id = :customerId OR p.ipAddress = :ipAddress)")
    ProductViews findByProductIdAndCustomerIdOrIpAddress(Long productId, Long customerId, String ipAddress);


}
