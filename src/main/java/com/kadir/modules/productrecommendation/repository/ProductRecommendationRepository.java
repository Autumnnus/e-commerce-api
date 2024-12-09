package com.kadir.modules.productrecommendation.repository;

import com.kadir.modules.product.model.Product;
import com.kadir.modules.productrecommendation.model.ProductRecommendation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRecommendationRepository extends JpaRepository<ProductRecommendation, Long> {

    List<ProductRecommendation> findByProductId(Long productId);

    List<ProductRecommendation> findByRecommendedProductId(Long recommendedProductId);

    boolean existsByProductAndRecommendedProduct(Product product, Product recommendedProduct);
}
