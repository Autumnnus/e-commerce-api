package com.kadir.controller;

import com.kadir.dto.DtoProduct;
import com.kadir.dto.DtoProductIU;

public interface IProductRestController {

    RootEntity<DtoProduct> createProduct(DtoProductIU dtoProductIU);

    RootEntity<DtoProduct> updateProduct(Long id, DtoProductIU dtoProductIU);

    RootEntity<DtoProduct> deleteProduct(Long id);
}
