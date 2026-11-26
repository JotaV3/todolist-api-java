package com.jotave.todolist.api.controllers;

import com.jotave.todolist.api.dtos.request.TaskRequestDTO;
import com.jotave.todolist.api.dtos.response.TaskResponseDTO;
import com.jotave.todolist.model.entities.Task;
import com.jotave.todolist.api.mappers.TaskMapper;
import com.jotave.todolist.application.services.TaskService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Objects;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/tasks")
public class TaskController {

    @Autowired
    private TaskService service;

    @Autowired
    private TaskMapper mapper;

    @GetMapping("/me")
    public ResponseEntity<List<TaskResponseDTO>> findAllByUserId(
            @AuthenticationPrincipal Jwt jwt
    ){
        Long userId = getUserIdByJwt(jwt);
        List<TaskResponseDTO> dto = mapAllTasksToTaskResponseDTO(service.findAllByUserId(userId));

        return ResponseEntity.ok().body(dto);
    }

    @PostMapping("/me")
    public ResponseEntity<TaskResponseDTO> insert(
            @AuthenticationPrincipal Jwt jwt,
            @Valid @RequestBody TaskRequestDTO dto
    ){
        Long userId = getUserIdByJwt(jwt);
        Task task = service.insert(userId, dto);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/me")
                .buildAndExpand(task.getId())
                .toUri();

        return ResponseEntity.created(uri).body(mapper.toTaskResponseDTO(task));
    }

    @PutMapping("/{id}/me")
    public ResponseEntity<TaskResponseDTO> update(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable Long id,
            @Valid @RequestBody TaskRequestDTO dto
    ){
        Long userId = getUserIdByJwt(jwt);
        Task task = service.update(id, userId, dto);

        return ResponseEntity.ok().body(mapper.toTaskResponseDTO(task));
    }

    @DeleteMapping("/{id}/me")
    public ResponseEntity<Void> delete(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable Long id
    ){
        Long userId = getUserIdByJwt(jwt);
        service.delete(id, userId);

        return ResponseEntity.noContent().build();
    }

    private List<TaskResponseDTO> mapAllTasksToTaskResponseDTO(List<Task> taskList){
        return taskList
                .stream()
                .map(task -> mapper.toTaskResponseDTO(task))
                .toList();
    }

    private Long getUserIdByJwt(Jwt jwt){
        // gets the id of User through jwt.getSubject()
        return Long.valueOf(Objects.requireNonNull(jwt.getSubject()));
    }
}
