package com.jotave.todolist.application.services;

import com.jotave.todolist.api.dtos.request.UserRequestDTO;
import com.jotave.todolist.api.dtos.response.AuthResponseDTO;
import com.jotave.todolist.application.exceptions.ValidationException;
import com.jotave.todolist.application.validations.InvalidField;
import com.jotave.todolist.model.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthService {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;

    public AuthResponseDTO register(UserRequestDTO dto){
        if(userService.existsByEmail(dto.email())){
            throw new ValidationException(
                    List.of(new InvalidField("Email", "Email already registered."))
            );
        }

        User user = userService.save(dto);

        return toAuthResponseDTO(user);
    }

    private AuthResponseDTO toAuthResponseDTO(User user){
        String token = jwtService.generateToken(user);
        long expiresAt = jwtService.getExpirationSeconds();

        return new AuthResponseDTO(
                token,
                "Bearer",
                expiresAt,
                user.getName()
        );
    }
}
