package com.jotave.todolist.services;

import com.jotave.todolist.entities.Task;
import com.jotave.todolist.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
    @Autowired
    private TaskRepository repository;

    public List<Task> findAll(){
        return repository.findAll();
    }

    public Task findById(Long id){
        Optional<Task> optionalTask = repository.findById(id);

        return optionalTask.orElseThrow(() -> new ResourceNotFoundException(id));
    }
}
