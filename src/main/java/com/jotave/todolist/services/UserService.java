package com.jotave.todolist.services;

import com.jotave.todolist.entities.User;
import com.jotave.todolist.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository repository;

    public List<User> findAll(){
        return repository.findAll();
    }

    public User findById(Long id){
        Optional<User> optionalUser = repository.findById(id);

        return optionalUser.orElseThrow(() -> new ResourceNotFoundException(id));
    }
}
