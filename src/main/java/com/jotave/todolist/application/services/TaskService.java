package com.jotave.todolist.application.services;

import com.jotave.todolist.api.dtos.request.TaskRequestDTO;
import com.jotave.todolist.model.entities.Task;
import com.jotave.todolist.model.entities.User;
import com.jotave.todolist.api.mappers.TaskMapper;
import com.jotave.todolist.infra.repositories.TaskRepository;
import com.jotave.todolist.application.exceptions.DatabaseException;
import com.jotave.todolist.application.exceptions.ResourceNotFoundException;
import com.jotave.todolist.application.exceptions.ValidationException;
import com.jotave.todolist.application.validations.TaskValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    @Autowired
    private TaskRepository repository;

    @Autowired
    private TaskMapper mapper;

    @Autowired
    private TaskValidator validator;

    @Autowired
    private UserService userService;

    public List<Task> findAllByUserId(Long userId){
        return repository.findAllByUserId(userId);
    }

    public Task insert(Long userId, TaskRequestDTO dto){

        // id is null because the task is yet to be created
        var result = validator.validateTaskRequestDTO(null, userId, dto);

        if(result.isInvalid()){
            throw new ValidationException(result.getInvalidFieldList());
        }

        // return user if exists
        User user = userService.findById(userId);
        Task task = mapper.toEntity(dto, user);

        return repository.save(task);
    }

    public Task update(Long id, Long userId, TaskRequestDTO dto){
        Task task = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found. Id: " + id));

        var result = validator.validateTaskRequestDTO(id, userId, dto);

        if(result.isInvalid()){
            throw new ValidationException(result.getInvalidFieldList());
        }

        mapper.update(task, dto);

        return repository.save(task);
    }

    public void delete(Long id, Long userId){
        if(!repository.existsByIdAndUserId(id, userId)){
            throw new ResourceNotFoundException("Task not found. Id: " + id);
        }

        try{
            repository.deleteById(id);
        }catch (DataIntegrityViolationException e){
            throw new DatabaseException(e.getMessage());
        }
    }
}
