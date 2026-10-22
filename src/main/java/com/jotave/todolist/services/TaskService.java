package com.jotave.todolist.services;

import com.jotave.todolist.entities.Task;
import com.jotave.todolist.entities.User;
import com.jotave.todolist.repositories.TaskRepository;
import com.jotave.todolist.services.exceptions.DatabaseException;
import com.jotave.todolist.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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

    public Task update(Long id, Task data){
        try{
            Task task = repository.getReferenceById(id);
            updateTaskData(task, data);

            return repository.save(task);
        }
        catch(EntityNotFoundException e){
            throw new ResourceNotFoundException(id);
        }
    }

    public void delete(Long id){
        if(!repository.existsById(id)){
            throw new ResourceNotFoundException(id);
        }

        try{
            repository.deleteById(id);
        }catch (DataIntegrityViolationException e){
            throw new DatabaseException(e.getMessage());
        }
    }

    private void updateTaskData(Task task, Task data){
        task.setTitle(data.getTitle());
        task.setDescription(data.getDescription());
        task.setTaskStatusCode(data.getTaskStatusCode());
    }
}
