package com.jotave.todolist.resources;

import com.jotave.todolist.entities.User;
import com.jotave.todolist.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class UserResourceTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository repository;

    @Test
    public void shouldReturnAllUsers() throws Exception{
        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
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
                    "phone": "315464363",
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
   }

   private User createUser(){
        User user = new User(null, "João", "joao@gmail.com", "41241", "512512");

        return repository.save(user);
   }
}
