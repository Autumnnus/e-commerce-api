package com.kadir.modules.product.service;

import com.kadir.common.utils.pagination.RestPageableEntity;
import com.kadir.modules.product.dto.DtoProduct;
import com.kadir.modules.product.dto.DtoProductIU;

public interface IProductService {

    DtoProduct createProduct(DtoProductIU dtoProduct);

    DtoProduct updateProduct(Long id, DtoProductIU dtoProduct);

    DtoProduct deleteProduct(Long id);

    RestPageableEntity<DtoProduct> getAllProducts(int pageNumber, int pageSize);

    DtoProduct getProductById(Long id);
}
