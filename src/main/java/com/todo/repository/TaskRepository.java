package com.todo.repository;

import com.todo.dto.TaskDto;
import com.todo.entities.Task;
import com.todo.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> {

    Optional<Task> findByUserAndId(User user, Long id);

    List<Task> findAllByUser(User user);

    void deleteByIdAndUser(Long id, User user);
    boolean existsByIdAndUser(Long id, User user);
}
