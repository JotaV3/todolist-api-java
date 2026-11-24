package com.jotave.todolist.application.services;

import com.jotave.todolist.api.dtos.request.TaskCreateRequestDTO;
import com.jotave.todolist.api.dtos.request.TaskUpdateRequestDTO;
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

    public List<Task> findAll(){
        return repository.findAll();
    }

    public Task findById(Long id){
        Optional<Task> optionalTask = repository.findById(id);

        return optionalTask
                .orElseThrow(() -> new ResourceNotFoundException("Task not found. Id: " + id));
    }

    public Task insert(TaskCreateRequestDTO dto, Long userId){
        var result = validator.validateTaskCreateRequestDTO(dto);

        if(result.isInvalid()){
            throw new ValidationException(result.getInvalidFieldList());
        }

        // return user if exist
        User user = userService.findById(userId);
        Task task = mapper.toEntity(dto, user);

        return repository.save(task);
    }

    public Task update(Long id, TaskUpdateRequestDTO dto){
        Task task = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found. Id: " + id));

        var result = validator.validateTaskUpdateRequestDTO(id, dto, task.getUser().getId());

        if(result.isInvalid()){
            throw new ValidationException(result.getInvalidFieldList());
        }

        mapper.update(task, dto);

        return repository.save(task);
    }

    public void delete(Long id){
        if(!repository.existsById(id)){
            throw new ResourceNotFoundException("Task not found. Id: " + id);
        }

        try{
            repository.deleteById(id);
        }catch (DataIntegrityViolationException e){
            throw new DatabaseException(e.getMessage());
        }
    }
}
