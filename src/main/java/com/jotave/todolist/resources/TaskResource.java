package com.jotave.todolist.resources;

import com.jotave.todolist.entities.Task;
import com.jotave.todolist.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskResource {
    @Autowired
    private TaskService service;

    @GetMapping
    public ResponseEntity<List<Task>> findAll(){
        List<Task> taskList = service.findAll();

        return ResponseEntity.ok().body(taskList);
    }
}
