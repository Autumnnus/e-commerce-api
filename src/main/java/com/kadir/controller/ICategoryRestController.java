package com.kadir.controller;

import com.kadir.dto.DtoCategory;
import com.kadir.dto.DtoCategoryIU;

import java.util.List;

public interface ICategoryRestController {

    RootEntity<DtoCategory> createCategory(DtoCategoryIU dtoCategoryIU);

    RootEntity<DtoCategory> updateCategory(Long id, DtoCategoryIU dtoCategoryIU);

    RootEntity<DtoCategory> deleteCategory(Long id);

    RootEntity<List<DtoCategory>> getAllCategories();

    RootEntity<DtoCategory> getCategoryById(Long id);
}
