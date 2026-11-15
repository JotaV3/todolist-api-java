package com.jotave.todolist.api.dtos.response;

import com.jotave.todolist.model.entities.enums.TaskStatus;

import java.time.LocalDate;

public record TaskResponseDTO(
    Long id,
    String title,
    String description,
    LocalDate dueDate,
    TaskStatus taskStatus,
    UserResponseDTO user
){}