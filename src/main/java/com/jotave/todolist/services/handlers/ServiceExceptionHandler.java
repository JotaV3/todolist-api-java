package com.jotave.todolist.services.handlers;

import com.jotave.todolist.services.exceptions.DatabaseException;
import com.jotave.todolist.services.exceptions.ResourceNotFoundException;
import com.jotave.todolist.services.exceptions.ValidationException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ServiceExceptionHandler {

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ValidationError> handleValidationException(
            ValidationException e,
            HttpServletRequest request
    ){
        HttpStatus status = HttpStatus.UNPROCESSABLE_CONTENT;

        ValidationError validationError = new ValidationError(
                status.value(),
                e.getMessage(),
                request.getRequestURI(),
                e.getInvalidFieldList()
        );

        return ResponseEntity.status(status).body(validationError);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<StandardError> handleResourceNotFoundException(
            ResourceNotFoundException e,
            HttpServletRequest request
    ){
        HttpStatus status = HttpStatus.NOT_FOUND;

        StandardError standardError = new StandardError(
                status.value(),
                e.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity.status(status).body(standardError);
    }

    @ExceptionHandler(DatabaseException.class)
    public ResponseEntity<StandardError> handleDatabaseException(
            DatabaseException e,
            HttpServletRequest request
    ){
        HttpStatus status = HttpStatus.CONFLICT;

        StandardError standardError = new StandardError(
            status.value(),
            e.getMessage(),
            request.getRequestURI()
        );

        return ResponseEntity.status(status).body(standardError);
    }
}
