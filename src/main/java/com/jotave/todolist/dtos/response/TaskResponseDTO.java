package com.jotave.todolist.dtos.response;

import com.jotave.todolist.entities.Task;
import com.jotave.todolist.entities.enums.TaskStatus;

public class TaskResponseDTO {
    private Long id;
    private String title;
    private String description;
    private TaskStatus taskStatus;
    private UserResponseDTO user;

    public TaskResponseDTO(Task task){
        this.id = task.getId();
        this.title = task.getTitle();
        this.description = task.getDescription();
        this.taskStatus = task.getTaskStatus();

        if(task.getUser() != null)
            this.user = new UserResponseDTO(task.getUser());
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public TaskStatus getTaskStatus() {
        return taskStatus;
    }

    public UserResponseDTO getUser() {
        return user;
    }
}
