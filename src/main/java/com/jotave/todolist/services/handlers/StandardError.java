package com.jotave.todolist.services.handlers;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.Instant;

public record StandardError(
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSX", timezone = "UTC")
    Instant timestamp,
    Integer status,
    String error,
    String path
) {
    public StandardError(Integer status, String error, String path){
        this(Instant.now(), status, error, path);
    }
}

