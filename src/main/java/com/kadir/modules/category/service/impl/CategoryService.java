package com.kadir.modules.category.service.impl;

import com.kadir.common.exception.BaseException;
import com.kadir.common.exception.ErrorMessage;
import com.kadir.common.exception.MessageType;
import com.kadir.modules.category.dto.CategoryDto;
import com.kadir.modules.category.dto.CategoryDtoIU;
import com.kadir.modules.category.model.Category;
import com.kadir.modules.category.repository.CategoryRepository;
import com.kadir.modules.category.service.ICategoryService;
import com.kadir.modules.product.model.Product;
import com.kadir.modules.product.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService implements ICategoryService {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    @Override
    public CategoryDto createCategory(CategoryDtoIU categoryDtoIU) {
        Category categoryEntity = modelMapper.map(categoryDtoIU, Category.class);
        Category savedCategory = categoryRepository.save(categoryEntity);
        return modelMapper.map(savedCategory, CategoryDto.class);
    }


    @Override
    public CategoryDto updateCategory(Long id, CategoryDtoIU categoryDtoIU) {
        Category existingCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new BaseException(new ErrorMessage(MessageType.GENERAL_EXCEPTION, "Category not found")));

        Category updatedCategory = modelMapper.map(categoryDtoIU, Category.class);
        updatedCategory.setId(existingCategory.getId());
        updatedCategory.setUpdatedAt(existingCategory.getUpdatedAt());
        updatedCategory.setCreatedAt(existingCategory.getCreatedAt());
        Category savedCategory = categoryRepository.save(updatedCategory);
        return modelMapper.map(savedCategory, CategoryDto.class);
    }


    @Transactional
    @Override
    public CategoryDto deleteCategory(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new BaseException(new ErrorMessage(MessageType.GENERAL_EXCEPTION, "Category not found")));
        List<Product> products = productRepository.findByCategoryId(id);
        products.forEach(product -> product.setCategory(null));
        productRepository.saveAll(products);
        categoryRepository.deleteById(id);
        return modelMapper.map(category, CategoryDto.class);
    }

    @Override
    public List<CategoryDto> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return modelMapper.map(categories, List.class);
    }

    @Override
    public CategoryDto getCategoryById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new BaseException(new ErrorMessage(MessageType.GENERAL_EXCEPTION, "Category not found")));
        return modelMapper.map(category, CategoryDto.class);
    }
}
