package com.jotave.todolist.api.mappers;

import com.jotave.todolist.api.dtos.request.UserRequestDTO;
import com.jotave.todolist.api.dtos.response.UserResponseDTO;
import com.jotave.todolist.model.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    User toEntity(UserRequestDTO dto);
    UserResponseDTO toUserResponseDTO(User user);

    @Mapping(target = "id", ignore = true)
    void update(@MappingTarget User user, UserRequestDTO dto);
}
