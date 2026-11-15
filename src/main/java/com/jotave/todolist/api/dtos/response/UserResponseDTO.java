package com.jotave.todolist.api.dtos.response;

public record UserResponseDTO(
    Long id,
    String name,
    String email,
    String phone
){}