package com.kadir.modules.review.repository;

import com.kadir.modules.review.model.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    Page<Review> findByProductId(Long productId, Pageable pageable);

    @Query("SELECT AVG(r.rating) FROM Review r WHERE r.product.id = :productId")
    Double getAverageRatingByProductId(Long productId);


    @Query("SELECT r FROM Review r WHERE r.user.id = :userId AND r.product.id = :productId")
    List<Review> findByUserAndProduct(@Param("userId") Long userId, @Param("productId") Long productId);

//    @Query("SELECT r FROM Review r WHERE r.user.id = :userId AND r.product.id = :productId")
//    Optional<Review> findByUserAndProduct(Long userId, Long productId);
}
