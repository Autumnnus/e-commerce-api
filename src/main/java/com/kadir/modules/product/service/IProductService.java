package com.kadir.modules.product.service;

import com.kadir.common.utils.pagination.RestPageableEntity;
import com.kadir.common.utils.pagination.RestPageableRequest;
import com.kadir.modules.product.dto.ProductCreateDto;
import com.kadir.modules.product.dto.ProductDto;
import com.kadir.modules.product.dto.ProductUpdateDto;

public interface IProductService {

    ProductDto createProduct(ProductCreateDto dtoProduct);

    ProductDto updateProduct(Long id, ProductUpdateDto dtoProduct);

    ProductDto deleteProduct(Long id);

    RestPageableEntity<ProductDto> getAllProducts(RestPageableRequest request);

    ProductDto getProductById(Long id);
}
