package com.todo.dto;

import com.todo.TaskStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class TaskDto {
    private long id;
    private String task;
    private TaskStatus taskStatus;
    private LocalDateTime time;
//    private String username;
}
