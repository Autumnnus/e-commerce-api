package com.kadir.controller;

import com.kadir.dto.DtoProduct;
import com.kadir.dto.DtoProductIU;
import com.kadir.utils.pagination.RestPageableEntity;
import com.kadir.utils.pagination.RestPageableRequest;

public interface IProductRestController {

    RootEntity<DtoProduct> createProduct(DtoProductIU dtoProductIU);

    RootEntity<DtoProduct> updateProduct(Long id, DtoProductIU dtoProductIU);

    RootEntity<DtoProduct> deleteProduct(Long id);

    RootEntity<RestPageableEntity<DtoProduct>> getAllProducts(RestPageableRequest request);

    RootEntity<DtoProduct> getProductById(Long id);
}
