package com.kadir.modules.category.controller;

import com.kadir.common.controller.RootEntity;
import com.kadir.modules.category.dto.CategoryCreateDto;
import com.kadir.modules.category.dto.CategoryDto;
import com.kadir.modules.category.dto.CategoryUpdateDto;

import java.util.List;

public interface ICategoryController {

    RootEntity<CategoryDto> createCategory(CategoryCreateDto categoryCreateDto);

    RootEntity<CategoryDto> updateCategory(Long id, CategoryUpdateDto categoryUpdateDto);

    RootEntity<CategoryDto> deleteCategory(Long id);

    RootEntity<List<CategoryDto>> getAllCategories();

    RootEntity<CategoryDto> getCategoryById(Long id);
}
