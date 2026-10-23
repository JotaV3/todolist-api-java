package com.jotave.todolist.resources;

import com.jotave.todolist.dtos.response.TaskResponseDTO;
import com.jotave.todolist.entities.Task;
import com.jotave.todolist.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskResource {
    @Autowired
    private TaskService service;

    @GetMapping
    public ResponseEntity<List<TaskResponseDTO>> findAll(){
        List<TaskResponseDTO> taskList = service.findAll()
                .stream()
                .map(TaskResponseDTO::new)
                .toList();

        return ResponseEntity.ok().body(taskList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> findById(@PathVariable Long id){
        Task task = service.findById(id);

        return ResponseEntity.ok().body(task);
    }

    @PostMapping
    public ResponseEntity<Task> insert(@RequestBody Task task){
        task = service.insert(task);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(task.getId()).toUri();

        return ResponseEntity.created(uri).body(task);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Task> update(@PathVariable Long id, @RequestBody Task task){
        task = service.update(id, task);

        return ResponseEntity.ok().body(task);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        service.delete(id);

        return ResponseEntity.noContent().build();
    }
}
