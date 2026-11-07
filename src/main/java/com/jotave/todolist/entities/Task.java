package com.jotave.todolist.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.jotave.todolist.entities.enums.TaskStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@Setter
@Getter
@Entity
@Table(name = "tb_task")
public class Task implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private LocalDate dueDate;
    private int taskStatusCode;

    @ManyToOne
    private User user;

    public Task(){

    }

    public Task(Long id, String title, String description, LocalDate dueDate, TaskStatus taskStatus, User user) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        if (taskStatus != null) setTaskStatusCode(taskStatus.getCode());
        this.user = user;
    }

    public TaskStatus getTaskStatus(){
        return TaskStatus.valueOf(taskStatusCode);
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public int getTaskStatusCode() {
        return taskStatusCode;
    }

    public void setTaskStatusCode(int taskStatusCode) {
        TaskStatus.valueOf(taskStatusCode);
        this.taskStatusCode = taskStatusCode;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Task task)) return false;
        return Objects.equals(id, task.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
