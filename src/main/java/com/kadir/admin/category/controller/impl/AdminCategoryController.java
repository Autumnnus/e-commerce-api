package com.kadir.admin.category.controller.impl;

import com.kadir.admin.category.controller.IAdminCategoryController;
import com.kadir.admin.category.service.IAdminCategoryService;
import com.kadir.common.controller.ApiResponse;
import com.kadir.common.controller.impl.RestBaseController;
import com.kadir.modules.category.dto.CategoryCreateDto;
import com.kadir.modules.category.dto.CategoryDto;
import com.kadir.modules.category.dto.CategoryUpdateDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/api/category")
public class AdminCategoryController extends RestBaseController implements IAdminCategoryController {

    @Autowired
    private IAdminCategoryService categoryService;

    @PostMapping
    @Override
    public ApiResponse<CategoryDto> createCategory(@RequestBody @Valid CategoryCreateDto categoryCreateDto) {
        return ApiResponse.success(categoryService.createCategory(categoryCreateDto));
    }

    @PutMapping("/{id}")
    @Override
    public ApiResponse<CategoryDto> updateCategory(@PathVariable(name = "id") Long id, @RequestBody @Valid CategoryUpdateDto categoryUpdateDto) {
        return ApiResponse.success(categoryService.updateCategory(id, categoryUpdateDto));
    }

    @DeleteMapping("/{id}")
    @Override
    public ApiResponse<CategoryDto> deleteCategory(@PathVariable(name = "id") Long id) {
        return ApiResponse.success(categoryService.deleteCategory(id));
    }


}
