package com.jotave.todolist.mappers;

import com.jotave.todolist.dtos.request.UserRequestDTO;
import com.jotave.todolist.dtos.response.UserResponseDTO;
import com.jotave.todolist.entities.User;
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
