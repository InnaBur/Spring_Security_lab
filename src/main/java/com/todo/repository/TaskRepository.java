package com.todo.repository;

import com.todo.entities.Task;
import com.todo.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> {

    Optional<Task> findByUserDetailsAndId(User user, Long id);
}
