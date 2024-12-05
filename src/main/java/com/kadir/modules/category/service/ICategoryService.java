package com.kadir.modules.category.service;

import com.kadir.modules.category.dto.CategoryDto;

import java.util.List;

public interface ICategoryService {

    List<CategoryDto> getAllCategories();

    CategoryDto getCategoryById(Long id);
}
