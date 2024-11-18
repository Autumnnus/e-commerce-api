package com.kadir.controller.impl;

import com.kadir.controller.ICategoryRestController;
import com.kadir.controller.RootEntity;
import com.kadir.dto.DtoCategory;
import com.kadir.service.ICategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @PutMapping(path = "/update/{id}")
    @Override
    public RootEntity<DtoCategory> updateCategory(@PathVariable(name = "id") Long id, @RequestBody @Valid DtoCategory dtoCategory) {
        return RootEntity.success(categoryService.updateCategory(id, dtoCategory));
    }

    @DeleteMapping("/delete/{id}")
    @Override
    public RootEntity<DtoCategory> deleteCategory(@PathVariable(name = "id") Long id) {
        return RootEntity.success(categoryService.deleteCategory(id));
    }
}
