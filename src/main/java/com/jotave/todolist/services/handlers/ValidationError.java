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
    public ValidationError(Instant timestamp, Integer status, String error, String path, ValidatorResult invalidFieldsList) {
        this.timestamp = Instant.now();
        this.status = status;
        this.error = error;
        this.path = path;
        this.invalidFieldsList = invalidFieldsList;
    }
}
