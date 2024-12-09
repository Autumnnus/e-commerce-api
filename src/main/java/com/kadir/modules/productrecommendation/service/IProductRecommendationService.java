package com.kadir.modules.productrecommendation.service;

import com.kadir.modules.product.dto.ProductDto;
import com.kadir.modules.productrecommendation.dto.ProductRecommendationDto;

import java.util.List;

public interface IProductRecommendationService {

    List<ProductRecommendationDto> getRecommendationsByProduct(Long productId);

    List<ProductDto> generateDailyRecommendations();
}
