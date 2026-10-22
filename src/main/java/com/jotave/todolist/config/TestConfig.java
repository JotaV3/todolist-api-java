package com.jotave.todolist.config;

import com.jotave.todolist.entities.Task;
import com.jotave.todolist.entities.User;
import com.jotave.todolist.entities.enums.TaskStatus;
import com.jotave.todolist.repositories.TaskRepository;
import com.jotave.todolist.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.Arrays;

@Configuration
@Profile("test")
public class TestConfig implements CommandLineRunner {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Override
    public void run(String... args) throws Exception {
        User user1 = new User(null, "João", "joao@gmail.com", "3121041245", "1234");
        User user2 = new User(null, "Felipe", "felipe@gmail.com", "314121315", "5122");

        userRepository.saveAll(Arrays.asList(user1, user2));

        Task task1 = new Task(null, "Arrumar a cama", "tal tal tal", TaskStatus.InProgress, user1);
        Task task2 = new Task(null, "Fazer dever de casa", "tal tal tal", TaskStatus.Completed, user1);
        Task task3 = new Task(null, "Lavar a louça", "tal tal tal", TaskStatus.Paused, user2);

        taskRepository.saveAll(Arrays.asList(task1, task2, task3));
    }
}
