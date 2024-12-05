package com.kadir.modules.category.controller.impl;

import com.kadir.common.controller.RootEntity;
import com.kadir.common.controller.impl.RestBaseController;
import com.kadir.modules.category.controller.ICategoryController;
import com.kadir.modules.category.dto.CategoryDto;
import com.kadir.modules.category.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/rest/api/category")
public class CategoryController extends RestBaseController implements ICategoryController {

    @Autowired
    private ICategoryService categoryService;

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
