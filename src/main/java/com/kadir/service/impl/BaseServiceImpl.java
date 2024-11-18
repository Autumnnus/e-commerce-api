package com.kadir.service.impl;

import com.kadir.service.IBaseService;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public abstract class BaseServiceImpl<T, DtoCreate, DtoResponse> implements IBaseService<T, DtoCreate, DtoResponse> {

    protected abstract JpaRepository<T, Long> getRepository();

    protected abstract T mapDtoToEntity(DtoCreate dto, T existingEntity);

    protected abstract DtoResponse mapEntityToDto(T entity);

    @Override
    public DtoResponse create(DtoCreate dto) {
        T entity = mapDtoToEntity(dto, null);
        entity = getRepository().save(entity);
        return mapEntityToDto(entity);
    }

    @Override
    public DtoResponse update(Long id, DtoCreate dto) {
        T existingEntity = getRepository().findById(id).orElseThrow(() ->
                new RuntimeException("Entity not found"));

        T updatedEntity = mapDtoToEntity(dto, existingEntity);
        updatedEntity = getRepository().save(updatedEntity);
        return mapEntityToDto(updatedEntity);
    }

    @Override
    public DtoResponse delete(Long id) {
        T entity = getRepository().findById(id).orElseThrow(() ->
                new RuntimeException("Entity not found"));

        getRepository().delete(entity);
        return mapEntityToDto(entity);
    }

    @Override
    public Optional<T> findById(Long id) {
        return getRepository().findById(id);
    }
}
