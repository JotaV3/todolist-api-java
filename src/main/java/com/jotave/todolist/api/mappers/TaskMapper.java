package com.jotave.todolist.api.mappers;

import com.jotave.todolist.api.dtos.request.TaskRequestDTO;
import com.jotave.todolist.api.dtos.response.TaskResponseDTO;
import com.jotave.todolist.model.entities.Task;
import com.jotave.todolist.model.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface TaskMapper {

    @Mapping(target = "id", ignore = true)
    Task toEntity(TaskRequestDTO dto, User user);
    TaskResponseDTO toTaskResponseDTO(Task task);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    void update(@MappingTarget Task task, TaskRequestDTO dto);
}
