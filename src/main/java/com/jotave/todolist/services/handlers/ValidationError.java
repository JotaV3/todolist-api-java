package com.jotave.todolist.services.handlers;

import com.jotave.todolist.services.validations.InvalidField;

import java.time.Instant;
import java.util.List;

public record ValidationError(
        Instant timestamp,
        Integer status,
        String error,
        String path,
        List<InvalidField> invalidFieldsList
) {
    public ValidationError(Integer status, String error, String path, List<InvalidField> invalidFieldsList) {
        this(Instant.now(), status, error, path, invalidFieldsList);
    }
}
