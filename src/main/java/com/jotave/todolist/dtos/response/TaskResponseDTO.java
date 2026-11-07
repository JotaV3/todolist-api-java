package com.jotave.todolist.dtos.response;

import com.jotave.todolist.entities.enums.TaskStatus;

import java.time.LocalDate;

public record TaskResponseDTO(
    Long id,
    String title,
    String description,
    LocalDate dueDate,
    TaskStatus taskStatus,
    UserResponseDTO user
){}