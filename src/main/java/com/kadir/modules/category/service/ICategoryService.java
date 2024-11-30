package com.kadir.modules.category.service;

import com.kadir.modules.category.dto.DtoCategory;
import com.kadir.modules.category.dto.DtoCategoryIU;

import java.util.List;

public interface ICategoryService {

    DtoCategory createCategory(DtoCategoryIU dtoCategoryIU);

    DtoCategory updateCategory(Long id, DtoCategoryIU dtoCategoryIU);

    DtoCategory deleteCategory(Long id);

    List<DtoCategory> getAllCategories();

    DtoCategory getCategoryById(Long id);
}
