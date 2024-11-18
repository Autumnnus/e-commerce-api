package com.kadir.service;

import com.kadir.dto.DtoCategory;
import com.kadir.dto.DtoCategoryIU;

public interface ICategoryService {

    DtoCategory createCategory(DtoCategoryIU dtoCategoryIU);

    DtoCategory updateCategory(Long id, DtoCategoryIU dtoCategoryIU);

    DtoCategory deleteCategory(Long id);
}
