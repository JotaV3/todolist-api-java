package com.jotave.todolist.dtos.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserRequestDTO(
    @NotBlank(message = "Name is required.")
    @Size(max = 100, message = "Name must have at most 100 characters.")
    String name,

    @NotBlank(message = "Email is required.")
    @Email(message = "Email must be valid.")
    @Size(max = 255, message = "Email must have at most 255 characters.")
    String email,

    @NotBlank(message = "Phone is required.")
    @Size(max = 20, message = "Phone must have at most 20 characters.")
    String phone,

    @NotBlank(message = "Password is required.")
    @Size(min = 8, max = 100,
            message = "Password must have between 8 and 100 characters.")
    String password
){}

