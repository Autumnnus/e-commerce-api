package com.kadir.modules.product.controller.impl;

import com.kadir.common.constants.Paths;
import com.kadir.common.controller.ApiResponse;
import com.kadir.common.controller.impl.RestBaseController;
import com.kadir.common.utils.pagination.RestPageableEntity;
import com.kadir.common.utils.pagination.RestPageableRequest;
import com.kadir.modules.product.controller.IProductController;
import com.kadir.modules.product.dto.ProductAIRequestDto;
import com.kadir.modules.product.dto.ProductCreateDto;
import com.kadir.modules.product.dto.ProductDto;
import com.kadir.modules.product.dto.ProductUpdateDto;
import com.kadir.modules.product.service.IProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController extends RestBaseController implements IProductController {

    @Autowired
    private IProductService productService;

    @PreAuthorize("hasRole('SELLER')")
    @PostMapping(Paths.BASE_PATH + "/product")
    @Override
    public ApiResponse<ProductDto> createProduct(@RequestBody @Valid ProductCreateDto productCreateDto) {
        return ApiResponse.success(productService.createProduct(productCreateDto));
    }

    @PreAuthorize("hasRole('SELLER') or hasRole('ADMIN')")
    @PutMapping(Paths.BASE_PATH + "/product/{id}")
    @Override
    public ApiResponse<ProductDto> updateProduct(@PathVariable(name = "id") Long id, @RequestBody @Valid ProductUpdateDto productUpdateDto) {
        return ApiResponse.success(productService.updateProduct(id, productUpdateDto));
    }

    @PreAuthorize("hasRole('SELLER') or hasRole('ADMIN')")
    @DeleteMapping(Paths.BASE_PATH + "/product/{id}")
    @Override
    public ApiResponse<ProductDto> deleteProduct(@PathVariable(name = "id") Long id) {
        return ApiResponse.success(productService.deleteProduct(id));
    }


    @GetMapping(Paths.PUBLIC_BASE_PATH + "/product")
    @Override
    public ApiResponse<RestPageableEntity<ProductDto>> getAllProducts(RestPageableRequest request) {
        return ok(productService.getAllProducts(request));
    }

    @GetMapping(Paths.PUBLIC_BASE_PATH + "/product/{id}")
    @Override
    public ApiResponse<ProductDto> getProductById(@PathVariable(name = "id") Long id) {
        return ApiResponse.success(productService.getProductById(id));
    }

    @PostMapping(Paths.PUBLIC_BASE_PATH + "/public/product/ai-recommendation")
    @Override
    public ApiResponse<List<String>> getProductRecommendationByAI(@RequestBody @Valid ProductAIRequestDto productAIRequestDto) {
        return ok(productService.getProductRecommendationByAI(productAIRequestDto));
    }
}
