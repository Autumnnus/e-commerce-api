package com.kadir.modules.category.controller.impl;

import com.kadir.common.controller.RootEntity;
import com.kadir.common.controller.impl.RestBaseController;
import com.kadir.modules.category.controller.ICategoryController;
import com.kadir.modules.category.dto.CategoryCreateDto;
import com.kadir.modules.category.dto.CategoryDto;
import com.kadir.modules.category.dto.CategoryUpdateDto;
import com.kadir.modules.category.service.ICategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/api/category")
public class CategoryController extends RestBaseController implements ICategoryController {

    @Autowired
    private ICategoryService categoryService;

    @PostMapping
    @Override
    public RootEntity<CategoryDto> createCategory(@RequestBody @Valid CategoryCreateDto categoryCreateDto) {
        return RootEntity.success(categoryService.createCategory(categoryCreateDto));
    }

    @PutMapping("/{id}")
    @Override
    public RootEntity<CategoryDto> updateCategory(@PathVariable(name = "id") Long id, @RequestBody @Valid CategoryUpdateDto categoryUpdateDto) {
        return RootEntity.success(categoryService.updateCategory(id, categoryUpdateDto));
    }

    @DeleteMapping("/{id}")
    @Override
    public RootEntity<CategoryDto> deleteCategory(@PathVariable(name = "id") Long id) {
        return RootEntity.success(categoryService.deleteCategory(id));
    }

    @GetMapping
    @Override
    public RootEntity<List<CategoryDto>> getAllCategories() {
        return RootEntity.success(categoryService.getAllCategories());
    }

    @GetMapping("/{id}")
    @Override
    public RootEntity<CategoryDto> getCategoryById(@PathVariable(name = "id") Long id) {
        return RootEntity.success(categoryService.getCategoryById(id));
    }
}
