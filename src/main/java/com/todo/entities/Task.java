package com.todo.entities;

import com.todo.TaskStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String task;

    @Enumerated(EnumType.STRING)
    private TaskStatus taskStatus = TaskStatus.PLANNED;

    private LocalDateTime time = LocalDateTime.now();

//    @ManyToOne
//    @JoinColumn(name="user_id");
//    private UserEntity user;

    public Task() {
    }

    public Task(String task) {
        this.task = task;
    }
}
