package com.jotave.todolist.infra.repositories;

import com.jotave.todolist.model.entities.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> {
    @Query("""
        SELECT t
        FROM Task t
        WHERE t.user.id = :userId AND t.title = :title
    """)
    List<Task> findTaskByTitleAndUserId(
            @Param("title") String title,
            @Param("userId") Long userId
    );

    @Query("""
        SELECT t
        FROM Task t
        WHERE t.user.id = :userId
    """)
    List<Task> findAllByUserId(
            @Param("userId") Long userId
    );

    @Query("""
        SELECT t
        FROM Task t
        WHERE t.id = :id
        AND t.user.id = :userId
    """)
    boolean existsByIdAndUserId(
            @Param("id") Long id,
            @Param("userId")Long userId
    );
}
