package com.kadir.modules.productrecommendation.service.impl;

import com.kadir.common.enums.RecommendationType;
import com.kadir.modules.product.dto.ProductDto;
import com.kadir.modules.product.model.Product;
import com.kadir.modules.product.repository.ProductRepository;
import com.kadir.modules.productrecommendation.dto.ProductRecommendationDto;
import com.kadir.modules.productrecommendation.model.ProductRecommendation;
import com.kadir.modules.productrecommendation.repository.ProductRecommendationRepository;
import com.kadir.modules.productrecommendation.service.IProductRecommendationService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductRecommendationService implements IProductRecommendationService {

    private final ProductRecommendationRepository productRecommendationRepository;
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    public List<ProductRecommendation> generateRecommendationsForProduct(Product product) {
        List<Product> similarProducts = productRepository.findByCategoryId(product.getCategory().getId());

        List<ProductRecommendation> recommendations = new ArrayList<>();
        for (Product similarProduct : similarProducts) {
            if (!similarProduct.getId().equals(product.getId())) {
                boolean exists = productRecommendationRepository.existsByProductAndRecommendedProduct(product, similarProduct);
                if (!exists) {
                    ProductRecommendation recommendation = new ProductRecommendation();
                    recommendation.setProduct(product);
                    recommendation.setRecommendedProduct(similarProduct);
                    recommendation.setRecommendationType(RecommendationType.SIMILAR);
                    recommendations.add(recommendation);
                }
            }
        }
        return recommendations;
    }

    @Override
    public List<ProductRecommendationDto> getRecommendationsByProduct(Long productId) {
        List<ProductRecommendation> byProductId = productRecommendationRepository.findByProductId(productId);
        return modelMapper.map(byProductId, new TypeToken<List<ProductRecommendationDto>>() {
        }.getType());

    }

    //    @Scheduled(fixedRate = 86400000)
    @Override
    public List<ProductDto> generateDailyRecommendations() {
        List<Product> allProducts = productRepository.findAll();
        for (Product product : allProducts) {
            List<ProductRecommendation> recommendations = generateRecommendationsForProduct(product);
            productRecommendationRepository.saveAll(recommendations);
        }
        return modelMapper.map(allProducts, new TypeToken<List<ProductDto>>() {
        }.getType());
    }
}
