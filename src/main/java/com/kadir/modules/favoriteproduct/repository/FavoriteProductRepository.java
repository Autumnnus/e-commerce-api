package com.kadir.modules.favoriteproduct.repository;

import com.kadir.modules.favoriteproduct.model.FavoriteProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FavoriteProductRepository extends JpaRepository<FavoriteProduct, Long> {

    Optional<FavoriteProduct> findByUserIdAndProductId(Long userId, Long productId);
}
