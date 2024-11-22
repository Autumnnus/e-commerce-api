package com.kadir.mapper;

import org.mapstruct.MappingTarget;

public interface BaseMapper<Entity, DtoCreate, DtoResponse> {

    Entity mapDtoToEntity(DtoCreate dto);

    Entity mapDtoToEntity(DtoCreate dto, @MappingTarget Entity existingEntity);

    DtoResponse mapEntityToDto(Entity entity);
}
