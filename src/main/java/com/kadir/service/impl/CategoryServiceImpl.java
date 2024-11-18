package com.kadir.service.impl;

import com.kadir.dto.DtoCategory;
import com.kadir.exception.BaseException;
import com.kadir.exception.ErrorMessage;
import com.kadir.exception.MessageType;
import com.kadir.model.Category;
import com.kadir.repository.CategoryRepository;
import com.kadir.service.ICategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

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
    public DtoCategory updateCategory(Long id, DtoCategory dtoCategory) {
        Optional<Category> optionalCategory = categoryRepository.findById(id);

        if (optionalCategory.isEmpty()) {
            throw new BaseException(new ErrorMessage(MessageType.GENERAL_EXCEPTION, "Category not found"));
        }
        optionalCategory.get().setName(dtoCategory.getName());
        optionalCategory.get().setDescription(dtoCategory.getDescription());
        optionalCategory.get().setUpdatedAt(LocalDateTime.now());

        Category updatedCategory = categoryRepository.save(optionalCategory.get());

        DtoCategory updatedDtoCategory = new DtoCategory();
        BeanUtils.copyProperties(updatedCategory, updatedDtoCategory);
        updatedDtoCategory.setCreatedDate(updatedCategory.getCreatedAt());
        updatedDtoCategory.setUpdatedDate(updatedCategory.getUpdatedAt());

        return dtoCategory;
    }


    @Override
    public DtoCategory deleteCategory(Long id) {
        Optional<Category> optionalCategory = categoryRepository.findById(id);

        if (optionalCategory.isEmpty()) {
            throw new BaseException(new ErrorMessage(MessageType.GENERAL_EXCEPTION, "Category not found"));
        }
        categoryRepository.delete(optionalCategory.get());

        DtoCategory deletedCategory = new DtoCategory();
        BeanUtils.copyProperties(optionalCategory.get(), deletedCategory);
        deletedCategory.setCreatedDate(optionalCategory.get().getCreatedAt());
        deletedCategory.setUpdatedDate(optionalCategory.get().getUpdatedAt());

        return deletedCategory;
    }

}
