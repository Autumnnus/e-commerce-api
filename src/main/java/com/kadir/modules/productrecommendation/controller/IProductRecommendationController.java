package com.kadir.modules.productrecommendation.controller;

import com.kadir.common.controller.ApiResponse;
import com.kadir.modules.product.dto.ProductDto;
import com.kadir.modules.productrecommendation.dto.ProductRecommendationDto;

import java.util.List;

public interface IProductRecommendationController {

    ApiResponse<List<ProductRecommendationDto>> getRecommendationsByProduct(Long productId);

    ApiResponse<List<ProductDto>> generateDailyRecommendations();
}
