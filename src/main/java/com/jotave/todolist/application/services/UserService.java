package com.jotave.todolist.application.services;

import com.jotave.todolist.api.dtos.request.UserRequestDTO;
import com.jotave.todolist.model.entities.User;
import com.jotave.todolist.api.mappers.UserMapper;
import com.jotave.todolist.infra.repositories.UserRepository;
import com.jotave.todolist.application.exceptions.DatabaseException;
import com.jotave.todolist.application.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Autowired
    private UserRepository repository;

    @Autowired
    private UserMapper mapper;

    public List<User> findAll(){
        return repository.findAll();
    }

    public User findById(Long id){
        Optional<User> optionalUser = repository.findById(id);

        return optionalUser
                .orElseThrow(() -> new ResourceNotFoundException("User not found. Id: " + id));
    }

    public boolean existsByEmail(String email){
        return repository.existsByEmail(email);
    }

    public User save(UserRequestDTO dto){
        User user = mapper.toEntity(dto);

        // encrypts the user's password
        String encryptedPassword = encoder.encode(dto.password());
        user.setPassword(encryptedPassword);

        return repository.save(user);
    }

    public User update(Long id, UserRequestDTO dto){
        try{
            User user = repository.getReferenceById(id);
            mapper.update(user, dto);

            return repository.save(user);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("User not found. Id: " + id);
        }
    }

    public void delete(Long id){
        if(!repository.existsById(id)){
            throw new ResourceNotFoundException("User not found. Id: " + id);
        }

        try {
            repository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException(e.getMessage());
        }

    }
}
