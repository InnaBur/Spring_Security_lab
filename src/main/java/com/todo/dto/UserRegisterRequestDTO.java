package com.todo.dto;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UserRegisterRequestDTO(
        @NotNull
        String username,
        @NotNull
//        @Size(min=2, message="user.password.invalid")
        String password
){

}

