package com.kadir.mapper;

import com.kadir.dto.DtoCategory;
import com.kadir.dto.DtoCategoryIU;
import com.kadir.model.Category;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    DtoCategory mapEntityToDto(Category entity);

    Category mapDtoToEntity(DtoCategoryIU dto);

    List<DtoCategory> mapEntityListToDtoList(List<Category> entities);
}
