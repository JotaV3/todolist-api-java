package com.jotave.todolist.controllers;

import com.jotave.todolist.api.dtos.request.UserRequestDTO;
import com.jotave.todolist.api.mappers.UserMapper;
import com.jotave.todolist.application.services.JwtService;
import com.jotave.todolist.model.entities.User;
import com.jotave.todolist.infra.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository repository;

    @Autowired
    private UserMapper mapper;

    @Autowired
    private JwtService jwtService;

    @Test
    public void shouldReturnUser() throws Exception {
        User user = createUser();
        String token = jwtService.generateToken(user);

        ResultActions result = mockMvc
                .perform(get("/api/users/me")
                        .header(AUTHORIZATION, "Bearer " + token)
                );

        // Result based on UserResponseDTO
        result
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(user.getId()))
                .andExpect(jsonPath("$.name").value(user.getName()))
                .andExpect(jsonPath("$.email").value(user.getEmail()))
                .andExpect(jsonPath("$.phone").value(user.getPhone()));
    }

    @Test
    public void shouldDeleteUser() throws Exception{
        User user = createUser();
        String token = jwtService.generateToken(user);

        ResultActions result = mockMvc
                .perform(delete("/api/users/me")
                        .header(AUTHORIZATION, "Bearer " + token));

        result
                .andExpect(status().isNoContent());

        assertFalse(repository.existsById(user.getId()));
    }

    private User createUser(){
        String email = "joao" + UUID.randomUUID() + "@gmail.com";

        UserRequestDTO dto = new UserRequestDTO(
                "João",
                email,
                "41241",
                "12345678"
        );

        User user = mapper.toEntity(dto);

        return repository.save(user);
   }
}
