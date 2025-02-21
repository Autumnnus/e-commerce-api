package com.kadir.modules.category.controller.impl;

import com.kadir.common.constants.Paths;
import com.kadir.common.controller.ApiResponse;
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
@RequestMapping(Paths.PUBLIC_BASE_PATH + "/category")
public class CategoryController extends RestBaseController implements ICategoryController {

    @Autowired
    private ICategoryService categoryService;

    @GetMapping
    @Override
    public ApiResponse<List<CategoryDto>> getAllCategories() {
        return ApiResponse.success(categoryService.getAllCategories());
    }

    @GetMapping("/{id}")
    @Override
    public ApiResponse<CategoryDto> getCategoryById(@PathVariable(name = "id") Long id) {
        return ApiResponse.success(categoryService.getCategoryById(id));
    }
}
