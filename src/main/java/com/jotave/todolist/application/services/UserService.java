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
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private UserMapper mapper;

    public List<User> findAll(){
        return repository.findAll();
    }

    public User findById(Long id){
        Optional<User> optionalUser = repository.findById(id);

        return optionalUser.orElseThrow(() -> new ResourceNotFoundException(id));
    }

    public User insert(UserRequestDTO dto){
        User user = mapper.toEntity(dto);

        return repository.save(user);
    }

    public User update(Long id, UserRequestDTO dto){
        try{
            User user = repository.getReferenceById(id);
            mapper.update(user, dto);

            return repository.save(user);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException(id);
        }
    }

    public void delete(Long id){
        if(!repository.existsById(id)){
            throw new ResourceNotFoundException(id);
        }

        try {
            repository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException(e.getMessage());
        }

    }
}
