package com.jotave.todolist.services.validations;

import com.jotave.todolist.dtos.request.TaskCreateRequestDTO;
import com.jotave.todolist.dtos.request.TaskUpdateRequestDTO;
import com.jotave.todolist.entities.Task;
import com.jotave.todolist.repositories.TaskRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;

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
        var dto = createTaskCreateRequestDTO();

        when(repository.findTaskByTitleAndUserId("Study Spring Boot", 1L))
                .thenReturn(Collections.emptyList());

        var result = validator.validateTaskCreateRequestDTO(dto);

        assertFalse(result.isInvalid());
    }

    @Test
    public void shouldRejectTitleAlreadyUsedByAnotherTaskDuringCreate(){
        var dto = createTaskCreateRequestDTO();

        Task otherTask = new Task();
        otherTask.setId(2L);

        when(repository.findTaskByTitleAndUserId("Study Spring Boot", 1L))
                .thenReturn(Collections.singletonList(otherTask));

        var result = validator.validateTaskCreateRequestDTO(dto);

        assertTrue(result.isInvalid());
        assertEquals("title", result.getInvalidFieldList().getFirst().name());
    }

    @Test
    public void shouldRejectTitleAlreadyUsedByAnotherTaskDuringUpdate(){
        var dto = createTaskUpdateRequestDTO();

        Task otherTask = new Task();
        otherTask.setId(2L);

        when(repository.findTaskByTitleAndUserId("Study Spring Boot", 5L))
                .thenReturn(Collections.singletonList(otherTask));

        var result = validator.validateTaskUpdateRequestDTO(1L, dto, 5L);

        assertTrue(result.isInvalid());
        assertEquals("title", result.getInvalidFieldList().getFirst().name());
    }

    @Test
    public void shouldNotRejectOwnTitleDuringUpdate(){
        var dto = createTaskUpdateRequestDTO();

        Task ownTask = new Task();
        ownTask.setId(1L);

        when(repository.findTaskByTitleAndUserId("Study Spring Boot", 5L))
                .thenReturn(Collections.singletonList(ownTask));

        var result = validator.validateTaskUpdateRequestDTO(1L, dto, 5L);

        assertFalse(result.isInvalid());
    }

    private TaskCreateRequestDTO createTaskCreateRequestDTO(){
        return new TaskCreateRequestDTO(
                "Study Spring Boot",
                "",
                LocalDate.of(2026, 11, 10),
                2,
                1L
        );
    }

    private TaskUpdateRequestDTO createTaskUpdateRequestDTO(){
        return new TaskUpdateRequestDTO(
                "Study Spring Boot",
                "",
                LocalDate.of(2026, 11, 10),
                2
        );
    }
}
