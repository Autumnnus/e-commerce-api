package com.kadir.service.impl;

import com.kadir.dto.DtoCategory;
import com.kadir.dto.DtoCategoryIU;
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

    public Category mapDtoToProduct(DtoCategoryIU dtoCategoryIU, Category existingCategory) {
        if (existingCategory != null) {
            existingCategory.setName(dtoCategoryIU.getName());
            existingCategory.setDescription(dtoCategoryIU.getDescription());
            existingCategory.setUpdatedAt(LocalDateTime.now());
            existingCategory.setCreatedAt(LocalDateTime.now());
            return existingCategory;
        } else {
            Category newCategory = new Category();
            newCategory.setCreatedAt(LocalDateTime.now());
            newCategory.setUpdatedAt(LocalDateTime.now());
            newCategory.setName(dtoCategoryIU.getName());
            newCategory.setDescription(dtoCategoryIU.getDescription());
            return newCategory;
        }
    }

    @Override
    public DtoCategory createCategory(DtoCategoryIU dtoCategoryIU) {
        Category savedCategory = categoryRepository.save(mapDtoToProduct(dtoCategoryIU, null));
        DtoCategory dtoCategory = new DtoCategory();
        BeanUtils.copyProperties(savedCategory, dtoCategory);

        dtoCategory.setName(savedCategory.getName());
        dtoCategory.setDescription(savedCategory.getDescription());
        dtoCategory.setCreatedDate(savedCategory.getCreatedAt());
        dtoCategory.setUpdatedDate(savedCategory.getUpdatedAt());
        return dtoCategory;
    }

    @Override
    public DtoCategory updateCategory(Long id, DtoCategoryIU dtoCategoryIU) {
        Optional<Category> optionalCategory = categoryRepository.findById(id);

        if (optionalCategory.isEmpty()) {
            throw new BaseException(new ErrorMessage(MessageType.GENERAL_EXCEPTION, "Category not found"));
        }
        Category existingCategory = optionalCategory.get();
        Category updatedCategory = mapDtoToProduct(dtoCategoryIU, existingCategory);
        Category savedCategory = categoryRepository.save(updatedCategory);

        DtoCategory updatedDtoProduct = new DtoCategory();
        BeanUtils.copyProperties(savedCategory, updatedDtoProduct);
        return updatedDtoProduct;

    }


    @Override
    public DtoCategory deleteCategory(Long id) {
        Optional<Category> optionalCategory = categoryRepository.findById(id);

        if (optionalCategory.isEmpty()) {
            throw new BaseException(new ErrorMessage(MessageType.GENERAL_EXCEPTION, "Category not found"));
        }
        DtoCategory dtoCategory = new DtoCategory();

        categoryRepository.delete(optionalCategory.get());
        BeanUtils.copyProperties(optionalCategory.get(), dtoCategory);
        dtoCategory.setCreatedDate(optionalCategory.get().getCreatedAt());
        dtoCategory.setUpdatedDate(optionalCategory.get().getUpdatedAt());

        return dtoCategory;
    }

}
