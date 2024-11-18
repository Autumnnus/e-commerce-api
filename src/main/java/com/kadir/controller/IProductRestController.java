package com.kadir.controller;

import com.kadir.dto.DtoProduct;
import com.kadir.dto.DtoProductIU;

import java.util.List;

public interface IProductRestController {

    RootEntity<DtoProduct> createProduct(DtoProductIU dtoProductIU);

    RootEntity<DtoProduct> updateProduct(Long id, DtoProductIU dtoProductIU);

    RootEntity<DtoProduct> deleteProduct(Long id);

    RootEntity<List<DtoProduct>> getAllProducts();

    RootEntity<DtoProduct> getProductById(Long id);
}
