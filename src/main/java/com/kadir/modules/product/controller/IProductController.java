package com.kadir.modules.product.controller;

import com.kadir.common.controller.ApiResponse;
import com.kadir.common.utils.pagination.RestPageableEntity;
import com.kadir.common.utils.pagination.RestPageableRequest;
import com.kadir.modules.product.dto.ProductCreateDto;
import com.kadir.modules.product.dto.ProductDto;
import com.kadir.modules.product.dto.ProductUpdateDto;

public interface IProductController {

    ApiResponse<ProductDto> createProduct(ProductCreateDto productCreateDto);

    ApiResponse<ProductDto> updateProduct(Long id, ProductUpdateDto productUpdateDto);

    ApiResponse<ProductDto> deleteProduct(Long id);

    ApiResponse<RestPageableEntity<ProductDto>> getAllProducts(RestPageableRequest request);

    ApiResponse<ProductDto> getProductById(Long id);
}
