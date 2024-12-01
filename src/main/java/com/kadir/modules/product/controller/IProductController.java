package com.kadir.modules.product.controller;

import com.kadir.common.controller.RootEntity;
import com.kadir.common.utils.pagination.RestPageableEntity;
import com.kadir.common.utils.pagination.RestPageableRequest;
import com.kadir.modules.product.dto.ProductDto;
import com.kadir.modules.product.dto.ProductDtoIU;

public interface IProductController {

    RootEntity<ProductDto> createProduct(ProductDtoIU productDtoIU);

    RootEntity<ProductDto> updateProduct(Long id, ProductDtoIU productDtoIU);

    RootEntity<ProductDto> deleteProduct(Long id);

    RootEntity<RestPageableEntity<ProductDto>> getAllProducts(RestPageableRequest request);

    RootEntity<ProductDto> getProductById(Long id);
}
