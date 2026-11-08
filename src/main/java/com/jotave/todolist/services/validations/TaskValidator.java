package com.jotave.todolist.services.validations;

import com.jotave.todolist.dtos.request.TaskCreateRequestDTO;
import com.jotave.todolist.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class TaskValidator {

    @Autowired
    private TaskRepository repository;

    public ValidatorResult validateTaskCreateRequestDTO(TaskCreateRequestDTO dto){
        var result = ValidatorResult.newValidationResult();

        boolean isListNotEmpty = repository.findTaskByTitleAndUserId(dto.title(), dto.userId()).isPresent();
        if(isListNotEmpty){
            result.addInvalidFieldToList(new InvalidField("title", "Task already exist."));
        }

        return result;
    }
}
