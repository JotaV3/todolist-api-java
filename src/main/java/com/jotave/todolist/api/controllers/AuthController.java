package com.jotave.todolist.api.controllers;

import com.jotave.todolist.api.dtos.request.UserRequestDTO;
import com.jotave.todolist.api.dtos.response.AuthResponseDTO;
import com.jotave.todolist.application.services.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    AuthService service;

    @PostMapping("/register")
    public ResponseEntity<AuthResponseDTO> register(@Valid @RequestBody UserRequestDTO dto){
        AuthResponseDTO authResponseDTO = service.register(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(authResponseDTO);
    }
}
