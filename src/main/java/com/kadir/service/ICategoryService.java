package com.kadir.service;

import com.kadir.dto.DtoCategory;

public interface ICategoryService {

    DtoCategory createCategory(DtoCategory dtoCategory);

    DtoCategory updateCategory(DtoCategory dtoCategory);

    void deleteCategory(Long id);
}
