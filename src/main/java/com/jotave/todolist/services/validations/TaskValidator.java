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

        boolean hasDuplicate = !repository
                .findTaskByTitleAndUserId(dto.title(), dto.userId())
                .isEmpty();

        if(hasDuplicate){
            result.addInvalidFieldToList(new InvalidField("title", "Task already exist."));
        }

        return result;
    }

    public ValidatorResult validateTaskUpdateRequestDTO(Long taskId, TaskUpdateRequestDTO dto, Long userId){
        var result = ValidatorResult.newValidationResult();

        // Verify if exists different tasks with the same title
        boolean hasDuplicate = repository
                .findTaskByTitleAndUserId(dto.title(), userId)
                .stream()
                .anyMatch(existingTask -> !existingTask.getId().equals(taskId));

        if(hasDuplicate){
            result.addInvalidFieldToList(
                    new InvalidField("title", "Task already exists.")
            );
        }

        return result;
    }
}
