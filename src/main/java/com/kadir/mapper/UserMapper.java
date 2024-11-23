package com.kadir.mapper;

import com.kadir.dto.DtoUser;
import com.kadir.model.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    DtoUser mapEntityToDto(User entity);

    User mapDtoToEntity(DtoUser dto);

    List<DtoUser> mapEntityListToDtoList(List<User> entities);
}
