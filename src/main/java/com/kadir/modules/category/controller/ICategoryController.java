package com.kadir.modules.category.controller;

import com.kadir.common.controller.RootEntity;
import com.kadir.modules.category.dto.CategoryDto;

import java.util.List;

public interface ICategoryController {

    RootEntity<List<CategoryDto>> getAllCategories();

    RootEntity<CategoryDto> getCategoryById(Long id);
}
