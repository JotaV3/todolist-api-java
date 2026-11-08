package com.jotave.todolist.controllers;

import com.jotave.todolist.entities.Task;
import com.jotave.todolist.entities.User;
import com.jotave.todolist.entities.enums.TaskStatus;
import com.jotave.todolist.repositories.TaskRepository;
import com.jotave.todolist.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDate;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

    private Task createTask(){
        User user = new User(
                null,
                "João",
                "joao" + UUID.randomUUID() + "@gmail.com",
                "41241",
                "12345678"
        );
        user = userRepository.save(user);

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
}
