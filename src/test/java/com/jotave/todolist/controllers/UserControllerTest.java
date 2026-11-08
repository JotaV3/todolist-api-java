package com.jotave.todolist.controllers;

import com.jotave.todolist.dtos.response.UserResponseDTO;
import com.jotave.todolist.entities.User;
import com.jotave.todolist.repositories.UserRepository;
import com.jotave.todolist.services.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import tools.jackson.databind.ObjectMapper;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
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
    private ObjectMapper objectMapper;

    @Test
    public void shouldReturnAllUsers() throws Exception{
        User user = createUser();

        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[?(@.id == %d)]"
                        .formatted(user.getId()))
                        .isNotEmpty());
    }

    @Test
    public void shouldReturnUser() throws Exception {
        User user = createUser();

        ResultActions result = mockMvc.perform(get("/users/" + user.getId()));

        result
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(user.getId()))
                .andExpect(jsonPath("$.name").value(user.getName()))
                .andExpect(jsonPath("$.email").value(user.getEmail()))
                .andExpect(jsonPath("$.phone").value(user.getPhone()));
    }

    @Test
    public void shouldSaveUser() throws Exception{
        String email = "joao" + UUID.randomUUID() + "@gmail.com";

        String json = """
                {
                    "name": "João",
                    "email": "%s",
                    "phone": "41241",
                    "password": "12345678"
                }
                """.formatted(email);

        ResultActions result = mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json));

        result
                .andExpect(status().isCreated())
                .andExpect((header().exists("Location")))
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value("João"))
                .andExpect(jsonPath("$.email").value(email))
                .andExpect(jsonPath("$.password").doesNotExist());

        UserResponseDTO responseDTO = objectMapper.readValue(
                result.andReturn().getResponse().getContentAsString(),
                UserResponseDTO.class
        );

        User user = repository.findById(responseDTO.id())
                .orElseThrow(() -> new ResourceNotFoundException("Resource not found. Id: " + responseDTO.id()));

        assertNotNull(responseDTO.id());
        assertEquals(email, user.getEmail());
    }

    @Test
    public void shouldDeleteUser() throws Exception{
        User user = createUser();

        mockMvc.perform(delete("/users/" + user.getId()))
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
