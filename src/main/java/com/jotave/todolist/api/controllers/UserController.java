package com.jotave.todolist.api.controllers;

import com.jotave.todolist.api.dtos.request.UserRequestDTO;
import com.jotave.todolist.api.dtos.response.UserResponseDTO;
import com.jotave.todolist.model.entities.User;
import com.jotave.todolist.api.mappers.UserMapper;
import com.jotave.todolist.application.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService service;

    @Autowired
    private UserMapper mapper;

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> findAll(){
        List<UserResponseDTO> userList = service.findAll()
                .stream()
                .map(user -> mapper.toUserResponseDTO(user))
                .toList();

        return ResponseEntity.ok().body(userList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> findById(@PathVariable Long id){
        User user = service.findById(id);

        return ResponseEntity.ok().body(mapper.toUserResponseDTO(user));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> update(@PathVariable Long id, @Valid @RequestBody UserRequestDTO dto){
        User user = service.update(id, dto);

        return ResponseEntity.ok().body(mapper.toUserResponseDTO(user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        service.delete(id);

        return ResponseEntity.noContent().build();
    }
}
