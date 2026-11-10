package com.jotave.todolist.services.handlers;

import com.jotave.todolist.services.validations.ValidatorResult;

import java.time.Instant;

public record ValidationError(
        Instant timestamp,
        Integer status,
        String error,
        String path,
        ValidatorResult invalidFieldsList
) {
    public ValidationError(Integer status, String error, String path, ValidatorResult invalidFieldsList) {
        this(Instant.now(), status, error, path, invalidFieldsList);
    }
}
