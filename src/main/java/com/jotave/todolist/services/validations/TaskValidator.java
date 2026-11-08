package com.jotave.todolist.services.validations;

import com.jotave.todolist.dtos.request.TaskCreateRequestDTO;
import com.jotave.todolist.dtos.request.TaskUpdateRequestDTO;
import com.jotave.todolist.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
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

    public ValidatorResult validateTaskUpdateRequestDTO(Long taskId, TaskUpdateRequestDTO dto, Long userId){
        var result = ValidatorResult.newValidationResult();

        // Verify if exists different tasks with the same title
        repository.findTaskByTitleAndUserId(dto.title(), userId)
                .filter(existingTask -> !existingTask.getId().equals(taskId))
                .ifPresent(existingTask -> result.addInvalidFieldToList(
                        new InvalidField("title", "Task already exists."))
                );

        return result;
    }
}
