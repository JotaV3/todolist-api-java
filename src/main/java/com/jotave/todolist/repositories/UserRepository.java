package com.jotave.todolist.repositories;

import com.jotave.todolist.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
