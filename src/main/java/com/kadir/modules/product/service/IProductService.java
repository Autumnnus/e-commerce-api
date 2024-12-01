package com.kadir.modules.product.service;

import com.kadir.common.utils.pagination.RestPageableEntity;
import com.kadir.modules.product.dto.ProductDto;
import com.kadir.modules.product.dto.ProductDtoIU;

public interface IProductService {

    ProductDto createProduct(ProductDtoIU dtoProduct);

    ProductDto updateProduct(Long id, ProductDtoIU dtoProduct);

    ProductDto deleteProduct(Long id);

    RestPageableEntity<ProductDto> getAllProducts(int pageNumber, int pageSize);

    ProductDto getProductById(Long id);
}
