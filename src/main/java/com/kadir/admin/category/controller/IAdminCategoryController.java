package com.kadir.admin.category.controller;

import com.kadir.common.controller.ApiResponse;
import com.kadir.modules.category.dto.CategoryCreateDto;
import com.kadir.modules.category.dto.CategoryDto;
import com.kadir.modules.category.dto.CategoryUpdateDto;

public interface IAdminCategoryController {

    ApiResponse<CategoryDto> createCategory(CategoryCreateDto categoryCreateDto);

    ApiResponse<CategoryDto> updateCategory(Long id, CategoryUpdateDto categoryUpdateDto);

    ApiResponse<CategoryDto> deleteCategory(Long id);

}
