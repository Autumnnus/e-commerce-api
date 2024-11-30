package com.kadir.modules.category.controller;

import com.kadir.common.controller.RootEntity;
import com.kadir.modules.category.dto.DtoCategory;
import com.kadir.modules.category.dto.DtoCategoryIU;

import java.util.List;

public interface ICategoryRestController {

    RootEntity<DtoCategory> createCategory(DtoCategoryIU dtoCategoryIU);

    RootEntity<DtoCategory> updateCategory(Long id, DtoCategoryIU dtoCategoryIU);

    RootEntity<DtoCategory> deleteCategory(Long id);

    RootEntity<List<DtoCategory>> getAllCategories();

    RootEntity<DtoCategory> getCategoryById(Long id);
}
