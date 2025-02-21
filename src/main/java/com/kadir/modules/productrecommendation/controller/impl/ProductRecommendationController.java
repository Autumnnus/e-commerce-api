package com.kadir.modules.productrecommendation.controller.impl;

import com.kadir.common.constants.Paths;
import com.kadir.common.controller.ApiResponse;
import com.kadir.common.controller.impl.RestBaseController;
import com.kadir.modules.product.dto.ProductDto;
import com.kadir.modules.productrecommendation.controller.IProductRecommendationController;
import com.kadir.modules.productrecommendation.dto.ProductRecommendationDto;
import com.kadir.modules.productrecommendation.service.IProductRecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(Paths.BASE_PATH + "/product-recommendation")
public class ProductRecommendationController extends RestBaseController implements IProductRecommendationController {

    @Autowired
    private IProductRecommendationService productRecommendationService;

    @GetMapping("/{productId}")
    @Override
    public ApiResponse<List<ProductRecommendationDto>> getRecommendationsByProduct(@PathVariable(name = "productId") Long productId) {
        return ok(productRecommendationService.getRecommendationsByProduct(productId));
    }

    @GetMapping
    @Override
    public ApiResponse<List<ProductDto>> generateDailyRecommendations() {
        return ok(productRecommendationService.generateDailyRecommendations());
    }
}
