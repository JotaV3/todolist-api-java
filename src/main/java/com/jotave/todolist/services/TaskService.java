package com.jotave.todolist.services;

import com.jotave.todolist.dtos.request.TaskCreateRequestDTO;
import com.jotave.todolist.dtos.request.TaskUpdateRequestDTO;
import com.jotave.todolist.entities.Task;
import com.jotave.todolist.entities.User;
import com.jotave.todolist.mappers.TaskMapper;
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
    private TaskMapper mapper;

    @Autowired
    private UserService userService;

    public List<Task> findAll(){
        return repository.findAll();
    }

    public Task findById(Long id){
        Optional<Task> optionalTask = repository.findById(id);

        return optionalTask.orElseThrow(() -> new ResourceNotFoundException(id));
    }

    public Task insert(TaskCreateRequestDTO dto, Long userId){
        // return user if exist
        User user = userService.findById(userId);
        Task task = mapper.toEntity(dto, user);

        return repository.save(task);
    }

    public Task update(Long id, TaskUpdateRequestDTO dto){
        try{
            Task task = repository.getReferenceById(id);
            mapper.update(task, dto);

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
}
