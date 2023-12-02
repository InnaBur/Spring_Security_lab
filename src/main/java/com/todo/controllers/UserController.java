package com.todo.controllers;

import com.todo.dto.TaskDto;
import com.todo.dto.UserDTO;
import com.todo.entities.Task;
import com.todo.entities.User;
import com.todo.exceptions.NotFoundException;
import com.todo.repository.TaskRepository;
import com.todo.repository.UserRepository;
import com.todo.services.AuthService;
import com.todo.services.TaskService;
import com.todo.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("users")
@EnableMethodSecurity
//@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
//@Secured("ROLE_ADMIN")
@PreAuthorize("hasAuthority('ADMIN')")
public class UserController {

    UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Gets user by ID", description = "Returns a user with the requested id")
    @GetMapping("/{user_id}")
    public ResponseEntity<Object> findUserById(@PathVariable Long user_id) {

        Optional<User> userOptional = userService.getUserById(user_id);
        if (userOptional.isEmpty()) {
            throw new NotFoundException("User was not found");
        }
        return ResponseEntity.ok(userOptional.get());
    }


    @Operation(summary = "Gets all users", description = "Returns all existing users")
    @GetMapping
    protected ResponseEntity<List<UserDTO>> allUsers() {

        List<UserDTO> usersDto = userService.getAllUsers();
        return ResponseEntity.ok(usersDto);
    }

    @Operation(summary = "Delete User", description = "Delete user from database")
    @DeleteMapping("/{user_id}")
    private ResponseEntity<Void> deleteUser(@PathVariable Long user_id) {
        userService.deleteUser(user_id);
        return ResponseEntity.noContent().build();
    }
}
