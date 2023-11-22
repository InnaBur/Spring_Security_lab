package com.todo.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.todo.entities.User;
import com.todo.enums.TaskStatus;
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
    @JsonIgnore
    private User user;
}
