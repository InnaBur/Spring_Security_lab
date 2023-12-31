package com.todo.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.todo.entities.User;
import com.todo.enums.TaskStatus;
import com.todo.lokalise.LanguageDefiner;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class TaskDtoResponse {

    @JsonIgnore
    private long id;

    @NotNull
    private String task;


    private TaskStatus taskStatus;
    private LocalDateTime time;
    @JsonIgnore
    private User user;

//    private String localizedTaskStatus;
//
//    public void setLocalizedTaskStatus() {
//        this.localizedTaskStatus = LanguageDefiner.toLocale(taskStatus.getMessageKey());
//    }

}
