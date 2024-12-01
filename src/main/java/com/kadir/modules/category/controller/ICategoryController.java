package com.kadir.modules.category.controller;

import com.kadir.common.controller.RootEntity;
import com.kadir.modules.category.dto.CategoryDto;
import com.kadir.modules.category.dto.CategoryDtoIU;

import java.util.List;

public interface ICategoryController {

    RootEntity<CategoryDto> createCategory(CategoryDtoIU categoryDtoIU);

    RootEntity<CategoryDto> updateCategory(Long id, CategoryDtoIU categoryDtoIU);

    RootEntity<CategoryDto> deleteCategory(Long id);

    RootEntity<List<CategoryDto>> getAllCategories();

    RootEntity<CategoryDto> getCategoryById(Long id);
}
