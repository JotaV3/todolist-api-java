package com.jotave.todolist.services.exceptions;

import com.jotave.todolist.services.validations.InvalidField;
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