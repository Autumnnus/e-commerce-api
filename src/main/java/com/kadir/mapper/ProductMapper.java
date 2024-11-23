package com.kadir.mapper;

import com.kadir.dto.DtoProduct;
import com.kadir.dto.DtoProductIU;
import com.kadir.model.Product;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    DtoProduct mapEntityToDto(Product entity);

    Product mapDtoToEntity(DtoProductIU dto);

    List<DtoProduct> mapEntityListToDtoList(List<Product> entities);
}
