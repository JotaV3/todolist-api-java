package com.jotave.todolist.dtos.response;

public record UserResponseDTO(
    Long id,
    String name,
    String email,
    String phone
){}