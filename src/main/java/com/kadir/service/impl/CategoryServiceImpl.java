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
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl extends BaseServiceImpl<Category, DtoCategoryIU, DtoCategory> implements ICategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    protected JpaRepository<Category, Long> getRepository() {
        return categoryRepository;
    }

    @Override
    protected Category mapDtoToEntity(DtoCategoryIU dto, Category existingCategory) {
        if (existingCategory == null) {
            existingCategory = new Category();
            existingCategory.setCreatedAt(LocalDateTime.now());
        }
        existingCategory.setUpdatedAt(LocalDateTime.now());
        existingCategory.setName(dto.getName());
        existingCategory.setDescription(dto.getDescription());
        return existingCategory;
    }

    @Override
    protected DtoCategory mapEntityToDto(Category entity) {
        DtoCategory dto = new DtoCategory();
        BeanUtils.copyProperties(entity, dto);
        return dto;
    }

    @Override
    public DtoCategory createCategory(DtoCategoryIU dtoCategoryIU) {
        Category savedCategory = categoryRepository.save(mapDtoToEntity(dtoCategoryIU, null));
        DtoCategory dtoCategory = new DtoCategory();
        BeanUtils.copyProperties(savedCategory, dtoCategory);

        dtoCategory.setName(savedCategory.getName());
        dtoCategory.setDescription(savedCategory.getDescription());
        return dtoCategory;
    }

    @Override
    public DtoCategory updateCategory(Long id, DtoCategoryIU dtoCategoryIU) {
        Optional<Category> optionalCategory = categoryRepository.findById(id);

        if (optionalCategory.isEmpty()) {
            throw new BaseException(new ErrorMessage(MessageType.GENERAL_EXCEPTION, "Category not found"));
        }
        Category existingCategory = optionalCategory.get();
        Category updatedCategory = mapDtoToEntity(dtoCategoryIU, existingCategory);
        Category savedCategory = categoryRepository.save(updatedCategory);
        DtoCategory dtoCategory = new DtoCategory();
        BeanUtils.copyProperties(savedCategory, dtoCategory);

        return dtoCategory;

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


        return dtoCategory;
    }

    @Override
    public List<DtoCategory> getAllCategories() {
        return categoryRepository.findAll().stream()
                .map(category -> {
                    DtoCategory dto = new DtoCategory();
                    BeanUtils.copyProperties(category, dto);
                    return dto;
                })
                .collect(Collectors.toList());
    }


    @Override
    public DtoCategory getCategoryById(Long id) {
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if (optionalCategory.isEmpty()) {
            throw new BaseException(new ErrorMessage(MessageType.GENERAL_EXCEPTION, "Category not found"));
        }
        Category category = optionalCategory.get();
        DtoCategory dto = new DtoCategory();
        BeanUtils.copyProperties(category, dto);
        return dto;
    }

}
