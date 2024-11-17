package com.kadir.controller.impl;

import com.kadir.controller.ICategoryRestController;
import com.kadir.controller.RootEntity;
import com.kadir.dto.DtoCategory;
import com.kadir.service.ICategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest/api/category")
public class CategoryRestControllerImpl extends RestBaseController implements ICategoryRestController {

    @Autowired
    private ICategoryService categoryService;

    @PostMapping("/create")
    @Override
    public RootEntity<DtoCategory> createCategory(@RequestBody @Valid DtoCategory dtoCategory) {
        return RootEntity.success(categoryService.createCategory(dtoCategory));
    }

    @Override
    public RootEntity<DtoCategory> updateCategory(@RequestBody @Valid DtoCategory dtoCategory) {
        return null;
    }

    @Override
    public RootEntity<DtoCategory> deleteCategory(Long id) {
        return null;
    }
}
