package com.kadir.controller.impl;

import com.kadir.controller.ICategoryRestController;
import com.kadir.controller.RootEntity;
import com.kadir.dto.DtoCategory;
import com.kadir.dto.DtoCategoryIU;
import com.kadir.service.ICategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/api/category")
public class CategoryRestController extends RestBaseController implements ICategoryRestController {

    @Autowired
    private ICategoryService categoryService;

    @PostMapping("/create")
    @Override
    public RootEntity<DtoCategory> createCategory(@RequestBody @Valid DtoCategoryIU dtoCategoryIU) {
        return RootEntity.success(categoryService.createCategory(dtoCategoryIU));
    }

    @PutMapping(path = "/update/{id}")
    @Override
    public RootEntity<DtoCategory> updateCategory(@PathVariable(name = "id") Long id, @RequestBody @Valid DtoCategoryIU dtoCategoryIU) {
        return RootEntity.success(categoryService.updateCategory(id, dtoCategoryIU));
    }

    @DeleteMapping("/delete/{id}")
    @Override
    public RootEntity<DtoCategory> deleteCategory(@PathVariable(name = "id") Long id) {
        return RootEntity.success(categoryService.deleteCategory(id));
    }

    @GetMapping("/all")
    @Override
    public RootEntity<List<DtoCategory>> getAllCategories() {
        return RootEntity.success(categoryService.getAllCategories());
    }

    @GetMapping("/{id}")
    @Override
    public RootEntity<DtoCategory> getCategoryById(@PathVariable(name = "id") Long id) {
        return RootEntity.success(categoryService.getCategoryById(id));
    }
}
