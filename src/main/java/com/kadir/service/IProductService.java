package com.kadir.service;

import com.kadir.dto.DtoProduct;
import com.kadir.dto.DtoProductIU;

public interface IProductService {

    DtoProduct createProduct(DtoProductIU dtoProduct);

    DtoProduct updateProduct(Long id, DtoProductIU dtoProduct);

    DtoProduct deleteProduct(Long id);
}
