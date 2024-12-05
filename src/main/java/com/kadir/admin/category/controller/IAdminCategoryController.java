package com.kadir.admin.category.controller;

import com.kadir.common.controller.RootEntity;
import com.kadir.modules.category.dto.CategoryCreateDto;
import com.kadir.modules.category.dto.CategoryDto;
import com.kadir.modules.category.dto.CategoryUpdateDto;

public interface IAdminCategoryController {

    RootEntity<CategoryDto> createCategory(CategoryCreateDto categoryCreateDto);

    RootEntity<CategoryDto> updateCategory(Long id, CategoryUpdateDto categoryUpdateDto);

    RootEntity<CategoryDto> deleteCategory(Long id);

}
