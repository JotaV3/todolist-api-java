package com.jotave.todolist.dtos.request;

import jakarta.validation.constraints.*;

public class TaskUpdateRequestDTO {
    @NotBlank(message = "Title is required.")
    @Size(max = 255, message = "Title must have at most 255 characters.")
    private String title;

    @Size(max = 255, message = "Description must have at most 255 characters.")
    private String description;

    @NotNull(message = "Task status is required.")
    @Min(value = 1, message = "Task status must be between 1 adn 3.")
    @Max(value = 3, message = "Task status must be between 1 and 3.")
    private int taskStatusCode;

    public TaskUpdateRequestDTO(){

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getTaskStatusCode() {
        return taskStatusCode;
    }

    public void setTaskStatusCode(int taskStatusCode) {
        this.taskStatusCode = taskStatusCode;
    }
}
