package com.jotave.todolist.mappers;

import com.jotave.todolist.dtos.request.TaskCreateRequestDTO;
import com.jotave.todolist.dtos.request.TaskUpdateRequestDTO;
import com.jotave.todolist.dtos.response.TaskResponseDTO;
import com.jotave.todolist.entities.Task;
import com.jotave.todolist.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface TaskMapper {

    @Mapping(target = "id", ignore = true)
    Task toEntity(TaskCreateRequestDTO dto, User user);
    TaskResponseDTO toTaskResponseDTO(Task task);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    void update(@MappingTarget Task task, TaskUpdateRequestDTO dto);
}
