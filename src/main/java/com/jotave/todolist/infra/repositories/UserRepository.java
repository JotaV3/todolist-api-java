package com.jotave.todolist.infra.repositories;

import com.jotave.todolist.model.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
