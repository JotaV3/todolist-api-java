package com.jotave.todolist.api.dtos.response;

public record AuthResponseDTO(
        String token,
        String type,
        Long expiresAt,
        String userName
) { }
