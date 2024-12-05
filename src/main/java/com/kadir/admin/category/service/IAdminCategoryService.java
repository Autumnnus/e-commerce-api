package com.kadir.admin.category.service;


import com.kadir.modules.category.dto.CategoryCreateDto;
import com.kadir.modules.category.dto.CategoryDto;
import com.kadir.modules.category.dto.CategoryUpdateDto;

public interface IAdminCategoryService {

    CategoryDto createCategory(CategoryCreateDto categoryCreateDto);

    CategoryDto updateCategory(Long id, CategoryUpdateDto categoryUpdateDto);

    CategoryDto deleteCategory(Long id);

}
