package com.jotave.todolist.services.validations;

import com.jotave.todolist.dtos.request.TaskUpdateRequestDTO;
import com.jotave.todolist.entities.Task;
import com.jotave.todolist.repositories.TaskRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TaskValidatorTest {

    @Mock
    private TaskRepository repository;

    @InjectMocks
    private TaskValidator validator;

    @Test
    public void shouldRejectTitleAlreadyUsedByAnotherTask(){
        var dto = new TaskUpdateRequestDTO(
            "Study Spring Boot",
                "",
                LocalDate.of(2026, 11, 10),
                2
        );

        Task otherTask = new Task();
        otherTask.setId(2L);

        when(repository.findTaskByTitleAndUserId("Study Spring Boot", 5L))
                .thenReturn(Optional.of(otherTask));

        var result = validator.validateTaskUpdateRequestDTO(1L, dto, 5L);

        assertTrue(result.isInvalid());
        assertEquals("title", result.getInvalidFieldList().getFirst().name());
    }
}
