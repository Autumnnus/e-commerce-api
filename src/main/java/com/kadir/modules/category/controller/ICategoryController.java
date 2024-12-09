package com.kadir.modules.category.controller;

import com.kadir.common.controller.ApiResponse;
import com.kadir.modules.category.dto.CategoryDto;

import java.util.List;

public interface ICategoryController {

    ApiResponse<List<CategoryDto>> getAllCategories();

    ApiResponse<CategoryDto> getCategoryById(Long id);
}
