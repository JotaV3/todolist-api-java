package com.jotave.todolist.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.jotave.todolist.entities.enums.TaskStatus;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "tb_task")
public class Task implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private int taskStatusCode;

    @ManyToOne
    private User user;

    public Task(){

    }

    public Task(Long id, String title, String description, TaskStatus taskStatus, User user) {
        this.id = id;
        this.title = title;
        this.description = description;
        if (taskStatus != null) setTaskStatusCode(taskStatus.getCode());
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
