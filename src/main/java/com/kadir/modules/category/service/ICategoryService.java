package com.kadir.modules.category.service;

import com.kadir.modules.category.dto.CategoryDto;
import com.kadir.modules.category.dto.CategoryDtoIU;

import java.util.List;

public interface ICategoryService {

    CategoryDto createCategory(CategoryDtoIU categoryDtoIU);

    CategoryDto updateCategory(Long id, CategoryDtoIU categoryDtoIU);

    CategoryDto deleteCategory(Long id);

    List<CategoryDto> getAllCategories();

    CategoryDto getCategoryById(Long id);
}
