package com.jotave.todolist.entities;

import com.jotave.todolist.entities.enums.TaskStatus;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "tb_task")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private Integer taskStatusCode;

    public Task(){

    }

    public Task(Long id, String title, String description, TaskStatus taskStatusCode) {
        this.id = id;
        this.title = title;
        this.description = description;
        setTaskStatusCode(taskStatusCode);
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

    public Integer getTaskStatusCode() {
        return taskStatusCode;
    }

    public void setTaskStatusCode(TaskStatus taskStatus) {
        if(taskStatusCode != null)
            this.taskStatusCode = taskStatus.getCode();
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
