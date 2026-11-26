package com.jotave.todolist.services.validations;

import com.jotave.todolist.api.dtos.request.TaskRequestDTO;
import com.jotave.todolist.application.validations.TaskValidator;
import com.jotave.todolist.model.entities.Task;
import com.jotave.todolist.infra.repositories.TaskRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TaskValidatorTest {

    @Mock
    private TaskRepository repository;

    @InjectMocks
    private TaskValidator validator;

    @Test
    public void shouldAllowCreateWhenTitleIsNotDuplicated(){
        TaskRequestDTO dto = createTaskRequestDTO();

        Long userId = 1L;

        when(repository.findTaskByTitleAndUserId("Study Spring Boot", userId))
                .thenReturn(Collections.emptyList());

        var result = validator.validateTaskRequestDTO(null, userId, dto);

        assertFalse(result.isInvalid());
    }

    @Test
    public void shouldRejectTitleAlreadyUsedByAnotherTask(){
        TaskRequestDTO dto = createTaskRequestDTO();

        // this task is a simulation of a task already created in the database
        Task otherTask = new Task();
        otherTask.setId(1L);

        Long userId = 1L;

        when(repository.findTaskByTitleAndUserId("Study Spring Boot", userId))
                .thenReturn(Collections.singletonList(otherTask));

        var result = validator.validateTaskRequestDTO(null, userId, dto);

        assertTrue(result.isInvalid());
        assertEquals("title", result.getInvalidFieldList().getFirst().name());
    }

    @Test
    public void shouldNotRejectOwnTitleDuringUpdate(){
        Long id = 1L; // task ID
        Long userId = 1L;

        // updated task data
        TaskRequestDTO dto = createTaskRequestDTO();

        // this task is a simulation of a task already created in the database
        Task task = new Task();
        task.setId(id);

        when(repository.findTaskByTitleAndUserId("Study Spring Boot", userId))
                .thenReturn(Collections.singletonList(task));

        var result = validator.validateTaskRequestDTO(id, userId, dto);

        assertFalse(result.isInvalid());
    }

    private TaskRequestDTO createTaskRequestDTO(){
        return new TaskRequestDTO(
                "Study Spring Boot",
                "",
                LocalDate.of(2026, 11, 10),
                2
        );
    }
}
