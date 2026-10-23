package com.jotave.todolist.resources;

import com.jotave.todolist.dtos.request.TaskCreateRequestDTO;
import com.jotave.todolist.dtos.request.TaskUpdateRequestDTO;
import com.jotave.todolist.dtos.response.TaskResponseDTO;
import com.jotave.todolist.entities.Task;
import com.jotave.todolist.services.TaskService;
import jakarta.validation.Valid;
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
    public ResponseEntity<TaskResponseDTO> findById(@PathVariable Long id){
        Task task = service.findById(id);

        return ResponseEntity.ok().body(new TaskResponseDTO(task));
    }

    @PostMapping
    public ResponseEntity<TaskResponseDTO> insert(@Valid @RequestBody TaskCreateRequestDTO dto){
        Task task = new Task();
        task.setTitle(dto.getTitle());
        task.setDescription(dto.getDescription());
        task.setTaskStatusCode(dto.getTaskStatusCode());

        task = service.insert(task, dto.getUserId());

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(task.getId())
                .toUri();

        return ResponseEntity.created(uri).body(new TaskResponseDTO(task));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskResponseDTO> update(@PathVariable Long id, @Valid @RequestBody TaskUpdateRequestDTO dto){
        Task data = new Task();
        data.setTitle(dto.getTitle());
        data.setDescription(dto.getDescription());
        data.setTaskStatusCode(dto.getTaskStatusCode());

        Task task = service.update(id, data);

        return ResponseEntity.ok().body(new TaskResponseDTO(task));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        service.delete(id);

        return ResponseEntity.noContent().build();
    }
}
