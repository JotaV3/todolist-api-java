package com.jotave.todolist.services;

import com.jotave.todolist.entities.Task;
import com.jotave.todolist.entities.User;
import com.jotave.todolist.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
    @Autowired
    private TaskRepository repository;

    @Autowired
    private UserService userService;

    public List<Task> findAll(){
        return repository.findAll();
    }

    public Task findById(Long id){
        Optional<Task> optionalTask = repository.findById(id);

        return optionalTask.orElseThrow(() -> new ResourceNotFoundException(id));
    }

    public Task insert(Task task){
        if(task.getUser() == null || task.getUser().getId() == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "User ID is required to create a task.");
        }

        User user = userService.findById(task.getUser().getId());
        task.setUser(user);

        return repository.save(task);
    }
}
