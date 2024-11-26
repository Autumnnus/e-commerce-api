package com.kadir.mapper;

import com.kadir.dto.DtoProduct;
import com.kadir.dto.DtoProductIU;
import com.kadir.model.Product;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    Product mapDtoToEntity(DtoProductIU dto);

    DtoProduct mapEntityToDto(Product entity);

    List<DtoProduct> mapEntityListToDtoList(List<Product> entities);
}

