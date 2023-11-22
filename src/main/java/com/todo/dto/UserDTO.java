package com.todo.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.todo.enums.Roles;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class UserDTO {

    @JsonIgnore
    private long userId;

    @NotNull

    private String username;

    @NotNull
    @Size(min=8, message="Password should have at least 8 characters")
    private String password;

    private Set<Roles> role;
}
