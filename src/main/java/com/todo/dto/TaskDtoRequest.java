package com.todo.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.todo.entities.User;
import com.todo.enums.TaskStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class TaskDtoRequest {

    @JsonIgnore
    private long id;

    private String task;
    private TaskStatus taskStatus;
    @JsonIgnore
    private LocalDateTime time;
    @JsonIgnore
    private User user;
}
