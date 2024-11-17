package com.kadir.controller;

import com.kadir.dto.DtoCategory;

public interface ICategoryRestController {

    RootEntity<DtoCategory> createCategory(DtoCategory dtoCategory);

    RootEntity<DtoCategory> updateCategory(DtoCategory dtoCategory);

    RootEntity<DtoCategory> deleteCategory(Long id);
}
