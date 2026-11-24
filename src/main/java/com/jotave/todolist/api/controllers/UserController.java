package com.jotave.todolist.api.controllers;

import com.jotave.todolist.api.dtos.request.UserRequestDTO;
import com.jotave.todolist.api.dtos.response.UserResponseDTO;
import com.jotave.todolist.model.entities.User;
import com.jotave.todolist.api.mappers.UserMapper;
import com.jotave.todolist.application.services.UserService;
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
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService service;

    @Autowired
    private UserMapper mapper;

    @GetMapping("/me")
    public ResponseEntity<UserResponseDTO> findById(@AuthenticationPrincipal Jwt jwt){

        // gets the of User through jwt.getSubject()
        Long id = Long.valueOf(Objects.requireNonNull(jwt.getSubject()));

        User user = service.findById(id);

        return ResponseEntity.ok().body(mapper.toUserResponseDTO(user));
    }

    @PutMapping("/me")
    public ResponseEntity<UserResponseDTO> update(
            @AuthenticationPrincipal Jwt jwt,
            @Valid @RequestBody UserRequestDTO dto
    ){
        // gets the id of User through jwt.getSubject()
        Long id = Long.valueOf(Objects.requireNonNull(jwt.getSubject()));

        User user = service.update(id, dto);

        return ResponseEntity.ok().body(mapper.toUserResponseDTO(user));
    }

    @DeleteMapping("/me")
    public ResponseEntity<Void> delete(@AuthenticationPrincipal Jwt jwt){

        // gets the of User through jwt.getSubject()
        Long id = Long.valueOf(Objects.requireNonNull(jwt.getSubject()));

        service.delete(id);

        return ResponseEntity.noContent().build();
    }
}
