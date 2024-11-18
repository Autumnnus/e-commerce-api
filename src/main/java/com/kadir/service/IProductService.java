package com.kadir.service;

import com.kadir.dto.DtoProduct;
import com.kadir.dto.DtoProductIU;

import java.util.List;

public interface IProductService {

    DtoProduct createProduct(DtoProductIU dtoProduct);

    DtoProduct updateProduct(Long id, DtoProductIU dtoProduct);

    DtoProduct deleteProduct(Long id);

    List<DtoProduct> getAllProducts();

    DtoProduct getProductById(Long id);
}
