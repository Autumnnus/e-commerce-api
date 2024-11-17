package com.kadir.service.impl;

import com.kadir.dto.DtoCategory;
import com.kadir.model.Category;
import com.kadir.repository.CategoryRepository;
import com.kadir.service.ICategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CategoryServiceImpl implements ICategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public Category convertToCategory(DtoCategory dtoCategory) {
        Category category = new Category();
        category.setName(dtoCategory.getName());
        category.setDescription(dtoCategory.getDescription());
        category.setCreatedAt(LocalDateTime.now());
        category.setUpdatedAt(LocalDateTime.now());
        return category;
    }

    @Override
    public DtoCategory createCategory(DtoCategory dtoCategory) {
        DtoCategory category = new DtoCategory();
        Category savedCategory = categoryRepository.save(convertToCategory(dtoCategory));
        BeanUtils.copyProperties(savedCategory, category);
        dtoCategory.setName(dtoCategory.getName());
        dtoCategory.setDescription(dtoCategory.getDescription());
        dtoCategory.setCreatedDate(savedCategory.getCreatedAt());
        dtoCategory.setUpdatedDate(savedCategory.getUpdatedAt());
        return dtoCategory;
    }

    @Override
    public DtoCategory updateCategory(DtoCategory dtoCategory) {
        return null;
    }

    @Override
    public void deleteCategory(Long id) {

    }
}
