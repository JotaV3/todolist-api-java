package com.jotave.todolist.controllers;

import com.jotave.todolist.dtos.request.TaskCreateRequestDTO;
import com.jotave.todolist.dtos.request.TaskUpdateRequestDTO;
import com.jotave.todolist.dtos.response.TaskResponseDTO;
import com.jotave.todolist.entities.Task;
import com.jotave.todolist.mappers.TaskMapper;
import com.jotave.todolist.services.TaskService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskService service;

    @Autowired
    private TaskMapper mapper;

    @GetMapping
    public ResponseEntity<List<TaskResponseDTO>> findAll(){
        List<TaskResponseDTO> dto = service.findAll()
                .stream()
                .map(task -> mapper.toTaskResponseDTO(task))
                .toList();

        return ResponseEntity.ok().body(dto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskResponseDTO> findById(@PathVariable Long id){
        Task task = service.findById(id);

        return ResponseEntity.ok().body(mapper.toTaskResponseDTO(task));
    }

    @PostMapping
    public ResponseEntity<TaskResponseDTO> insert(@Valid @RequestBody TaskCreateRequestDTO dto){
        Task task = service.insert(dto, dto.userId());

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(task.getId())
                .toUri();

        return ResponseEntity.created(uri).body(mapper.toTaskResponseDTO(task));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskResponseDTO> update(@PathVariable Long id, @Valid @RequestBody TaskUpdateRequestDTO dto){
        Task task = service.update(id, dto);

        return ResponseEntity.ok().body(mapper.toTaskResponseDTO(task));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        service.delete(id);

        return ResponseEntity.noContent().build();
    }
}
