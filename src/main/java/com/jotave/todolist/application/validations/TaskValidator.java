package com.jotave.todolist.application.validations;

import com.jotave.todolist.api.dtos.request.TaskRequestDTO;
import com.jotave.todolist.infra.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TaskValidator {

    @Autowired
    private TaskRepository repository;

    public ValidatorResult validateTaskRequestDTO(Long id, Long userId, TaskRequestDTO dto){
        var result = ValidatorResult.newValidationResult();

        // Verify if exists different tasks with the same title
        boolean hasDuplicate = repository
                .findTaskByTitleAndUserId(dto.title(), userId)
                .stream()
                .anyMatch(existingTask -> !existingTask.getId().equals(id));

        if(hasDuplicate){
            result.addInvalidFieldToList(
                    new InvalidField("title", "Task already exists.")
            );
        }

        return result;
    }
}
