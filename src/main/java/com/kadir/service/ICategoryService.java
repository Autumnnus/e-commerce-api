package com.kadir.service;

import com.kadir.dto.DtoCategory;
import com.kadir.dto.DtoCategoryIU;
import com.kadir.model.Category;

import java.util.List;

public interface ICategoryService extends IBaseService<Category, DtoCategoryIU, DtoCategory> {

    DtoCategory createCategory(DtoCategoryIU dtoCategoryIU);

    DtoCategory updateCategory(Long id, DtoCategoryIU dtoCategoryIU);

    DtoCategory deleteCategory(Long id);

    List<DtoCategory> getAllCategories();

    DtoCategory getCategoryById(Long id);
}
