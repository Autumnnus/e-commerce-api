package com.kadir.service;

import com.kadir.dto.DtoProduct;
import com.kadir.dto.DtoProductIU;
import com.kadir.utils.pagination.RestPageableEntity;

public interface IProductService {

    DtoProduct createProduct(DtoProductIU dtoProduct);

    DtoProduct updateProduct(Long id, DtoProductIU dtoProduct);

    DtoProduct deleteProduct(Long id);

    RestPageableEntity<DtoProduct> getAllProducts(int pageNumber, int pageSize);

    DtoProduct getProductById(Long id);
}
