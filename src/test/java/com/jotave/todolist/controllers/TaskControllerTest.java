package com.jotave.todolist.controllers;

import com.jotave.todolist.dtos.response.TaskResponseDTO;
import com.jotave.todolist.entities.Task;
import com.jotave.todolist.entities.User;
import com.jotave.todolist.entities.enums.TaskStatus;
import com.jotave.todolist.repositories.TaskRepository;
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

import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class TaskControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    TaskRepository repository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    public void shouldReturnAllTasks() throws Exception{
        Task task = createTask();

        mockMvc.perform(get("/tasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[?(@.id == %d)]"
                        .formatted(task.getId()))
                        .isNotEmpty());
    }

    @Test
    public void shouldReturnTask() throws Exception{
        Task task = createTask();

        ResultActions result = mockMvc.perform(get("/tasks/" + task.getId()));

        result
                .andExpect(jsonPath("$.id").value(task.getId()))
                .andExpect(jsonPath("$.title").value(task.getTitle()))
                .andExpect(jsonPath("$.description").value(task.getDescription()))
                .andExpect(jsonPath("$.dueDate").value(task.getDueDate().toString()))
                .andExpect(jsonPath("$.taskStatus").value(task.getTaskStatus().toString()))
                .andExpect(jsonPath("$.user").value(task.getUser()));
    }

    @Test
    public void shouldSaveTask() throws Exception{
        User user = createUser();

        String json = """
                {
                    "title": "Study Spring Boot",
                    "description": "Build a To-do List API REST",
                    "dueDate": "2026-11-10",
                    "taskStatusCode": 2,
                    "userId": %d
                }
                """.formatted(user.getId());

        ResultActions result = mockMvc.perform(post("/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json));

        result
                .andExpect(status().isCreated())
                .andExpect((header().exists("Location")))
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.description").value("Build a To-do List API REST"))
                .andExpect(jsonPath("$.dueDate").value("2026-11-10"))
                .andExpect(jsonPath("$.taskStatus").value(TaskStatus.valueOf(2).toString()))
                .andExpect(jsonPath("$.user.id").value(user.getId()))
                .andExpect(jsonPath("$.user.name").value(user.getName()))
                .andExpect(jsonPath("$.user.email").value(user.getEmail()))
                .andExpect(jsonPath("$.user.phone").value(user.getPhone()));

        TaskResponseDTO responseDTO = objectMapper.readValue(
                result.andReturn().getResponse().getContentAsString(),
                TaskResponseDTO.class
        );

        Task savedTask = repository.findById(responseDTO.id())
                .orElseThrow(() -> new ResourceNotFoundException("Resource not found. Id: " + responseDTO.id()));

        assertNotNull(responseDTO.id());
        assertEquals("Study Spring Boot", savedTask.getTitle());
        assertEquals(user.getId(), savedTask.getUser().getId());
    }

    private Task createTask(){
        User user = createUser();

        Task task = new Task(
                null,
                "Study Spring Boot",
                "Build a To-do List API REST",
                LocalDate.of(2026, 11, 10),
                TaskStatus.InProgress,
                user
        );

        return repository.save(task);
    }

    private User createUser(){
        User user = new User(
                null,
                "João",
                "joao" + UUID.randomUUID() + "@gmail.com",
                "41241",
                "12345678"
        );

        return userRepository.save(user);
    }
}
