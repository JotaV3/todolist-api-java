package com.jotave.todolist.dtos.response;

import com.jotave.todolist.entities.User;

public class UserResponseDTO {
    private Long id;
    private String name;
    private String email;
    private String phone;

    public UserResponseDTO(User user){
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.phone = user.getPhone();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }
}
