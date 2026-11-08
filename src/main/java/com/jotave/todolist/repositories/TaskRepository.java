package com.jotave.todolist.repositories;

import com.jotave.todolist.entities.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> {
    @Query("""
        SELECT t
        FROM Task t
        WHERE userId = :userId AND t.title = :title
    """)
    Optional<Task> findTaskByTitleAndUserId(@Param("title") String title, @Param("userId") Long userId);
}
