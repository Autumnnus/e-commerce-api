package com.kadir.service;

import com.kadir.dto.DtoCategory;

public interface ICategoryService {

    DtoCategory createCategory(DtoCategory dtoCategory);

    DtoCategory updateCategory(Long id, DtoCategory dtoCategory);

    DtoCategory deleteCategory(Long id);
}
