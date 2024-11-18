package com.kadir.service;

import java.util.Optional;

public interface IBaseService<T, DtoCreate, DtoResponse> {
    DtoResponse create(DtoCreate dto);

    DtoResponse update(Long id, DtoCreate dto);

    DtoResponse delete(Long id);

    Optional<T> findById(Long id);
}
