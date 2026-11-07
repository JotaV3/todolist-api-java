package com.jotave.todolist.dtos.request;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record TaskCreateRequestDTO(
    @NotBlank(message = "Title is required.")
    @Size(max = 255, message = "Title must have at most 255 characters.")
    String title,

    @Size(max = 255, message = "Description must have at most 255 characters.")
    String description,

    @NotNull(message = "Due date is required.")
    LocalDate dueDate,

    @NotNull(message = "Task status is required.")
    @Min(value = 1, message = "Task status must be between 1 adn 3.")
    @Max(value = 3, message = "Task status must be between 1 and 3.")
    Integer taskStatusCode,

    @NotNull(message = "User ID is required.")
    @Positive(message = "User ID must be positive.")
    Long userId
){}