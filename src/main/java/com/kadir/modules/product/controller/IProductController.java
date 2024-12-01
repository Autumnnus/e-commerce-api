package com.kadir.modules.product.controller;

import com.kadir.common.controller.RootEntity;
import com.kadir.common.utils.pagination.RestPageableEntity;
import com.kadir.common.utils.pagination.RestPageableRequest;
import com.kadir.modules.product.dto.ProductCreateDto;
import com.kadir.modules.product.dto.ProductDto;
import com.kadir.modules.product.dto.ProductUpdateDto;

public interface IProductController {

    RootEntity<ProductDto> createProduct(ProductCreateDto productCreateDto);

    RootEntity<ProductDto> updateProduct(Long id, ProductUpdateDto productUpdateDto);

    RootEntity<ProductDto> deleteProduct(Long id);

    RootEntity<RestPageableEntity<ProductDto>> getAllProducts(RestPageableRequest request);

    RootEntity<ProductDto> getProductById(Long id);
}
