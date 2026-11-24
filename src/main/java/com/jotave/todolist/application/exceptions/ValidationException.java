package com.jotave.todolist.application.exceptions;

import com.jotave.todolist.application.validations.InvalidField;
import lombok.Getter;

import java.util.List;

@Getter
public class ValidationException extends RuntimeException {
    private final List<InvalidField> invalidFieldList;

    public ValidationException(List<InvalidField> invalidFieldList) {
        super("Validation error.");
        this.invalidFieldList = invalidFieldList;
    }
}