package com.jotave.todolist.controllers;

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

    @Test
    public void shouldReturnAllUsers() throws Exception{
        User user = createUser();

        ResultActions result = mockMvc
                .perform(get("/api/users")
                        .with(user("test")));

        // Result based on UserResponseDTO
        result
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[?(@.id == %d)]"
                        .formatted(user.getId()))
                        .isNotEmpty());
    }

    @Test
    public void shouldReturnUser() throws Exception {
        User user = createUser();

        ResultActions result = mockMvc
                .perform(get("/api/users/" + user.getId())
                        .with(user("test")));

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

        ResultActions result = mockMvc
                .perform(delete("/api/users/" + user.getId())
                        .with(user("test")));

        result
                .andExpect(status().isNoContent());

        assertFalse(repository.existsById(user.getId()));
    }

    private User createUser(){
        String email = "joao" + UUID.randomUUID() + "@gmail.com";

        User user = new User(
                null,
                "João",
                email,
                "41241",
                "12345678"
        );

        return repository.save(user);
   }
}
