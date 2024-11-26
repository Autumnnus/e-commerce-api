package com.kadir.service.impl;

import com.kadir.dto.DtoCategory;
import com.kadir.dto.DtoCategoryIU;
import com.kadir.exception.BaseException;
import com.kadir.exception.ErrorMessage;
import com.kadir.exception.MessageType;
import com.kadir.mapper.CategoryMapper;
import com.kadir.model.Category;
import com.kadir.model.Product;
import com.kadir.repository.CategoryRepository;
import com.kadir.repository.ProductRepository;
import com.kadir.service.ICategoryService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl extends BaseServiceImpl<Category, DtoCategoryIU, DtoCategory> implements ICategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    protected JpaRepository<Category, Long> getRepository() {
        return categoryRepository;
    }

    @Override
    protected Category mapDtoToEntity(DtoCategoryIU dto, Category existingCategory) {
        return null;
    }

    @Override
    protected DtoCategory mapEntityToDto(Category entity) {
        return null;
    }

    @Override
    public DtoCategory createCategory(DtoCategoryIU dtoCategoryIU) {
        Category dtoToEntity = categoryMapper.mapDtoToEntity(dtoCategoryIU);
        Category savedCategory = categoryRepository.save(dtoToEntity);
        return categoryMapper.mapEntityToDto(savedCategory);
    }

    @Override
    public DtoCategory updateCategory(Long id, DtoCategoryIU dtoCategoryIU) {
        Category existingCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new BaseException(new ErrorMessage(MessageType.GENERAL_EXCEPTION, "Category not found")));

        Category updatedCategory = categoryMapper.mapDtoToEntity(dtoCategoryIU);
        updatedCategory.setId(existingCategory.getId());
        updatedCategory.setUpdatedAt(existingCategory.getUpdatedAt());
        updatedCategory.setCreatedAt(existingCategory.getCreatedAt());
        Category savedCategory = categoryRepository.save(updatedCategory);
        return categoryMapper.mapEntityToDto(savedCategory);
    }


    @Transactional
    @Override
    public DtoCategory deleteCategory(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new BaseException(new ErrorMessage(MessageType.GENERAL_EXCEPTION, "Category not found")));
        List<Product> products = productRepository.findByCategoryId(id);
        products.forEach(product -> product.setCategory(null));
        productRepository.saveAll(products);
        categoryRepository.deleteById(id);
        return categoryMapper.mapEntityToDto(category);
    }

    @Override
    public List<DtoCategory> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categoryMapper.mapEntityListToDtoList(categories);
    }

    @Override
    public DtoCategory getCategoryById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new BaseException(new ErrorMessage(MessageType.GENERAL_EXCEPTION, "Category not found")));
        return categoryMapper.mapEntityToDto(category);
    }
}
