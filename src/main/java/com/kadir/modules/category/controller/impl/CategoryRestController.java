package com.kadir.modules.category.controller.impl;

import com.kadir.common.controller.RootEntity;
import com.kadir.common.controller.impl.RestBaseController;
import com.kadir.modules.category.controller.ICategoryRestController;
import com.kadir.modules.category.dto.DtoCategory;
import com.kadir.modules.category.dto.DtoCategoryIU;
import com.kadir.modules.category.service.ICategoryService;
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
