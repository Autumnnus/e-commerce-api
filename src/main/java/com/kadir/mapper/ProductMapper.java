package com.kadir.mapper;

import com.kadir.dto.DtoProduct;
import com.kadir.dto.DtoProductIU;
import com.kadir.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(target = "id", ignore = true)
    Product mapDtoToEntity(DtoProductIU dto);

    DtoProduct mapEntityToDto(Product entity);

    List<DtoProduct> mapEntityListToDtoList(List<Product> entities);
}

