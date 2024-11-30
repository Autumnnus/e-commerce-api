package com.kadir.modules.product.controller;

import com.kadir.common.controller.RootEntity;
import com.kadir.common.utils.pagination.RestPageableEntity;
import com.kadir.common.utils.pagination.RestPageableRequest;
import com.kadir.modules.product.dto.DtoProduct;
import com.kadir.modules.product.dto.DtoProductIU;

public interface IProductRestController {

    RootEntity<DtoProduct> createProduct(DtoProductIU dtoProductIU);

    RootEntity<DtoProduct> updateProduct(Long id, DtoProductIU dtoProductIU);

    RootEntity<DtoProduct> deleteProduct(Long id);

    RootEntity<RestPageableEntity<DtoProduct>> getAllProducts(RestPageableRequest request);

    RootEntity<DtoProduct> getProductById(Long id);
}
