package com.todo.controllers;

import com.todo.dto.TaskDto;
import com.todo.dto.TaskDtoRequest;
import com.todo.dto.TaskDtoResponse;
import com.todo.entities.Task;
import com.todo.exceptions.NotFoundException;
import com.todo.services.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("tasks")
@SecurityRequirement(name = "Bearer Authentication")
@EnableMethodSecurity
//@RequiredArgsConstructor
public class TaskController {

   TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    @Operation(summary = "Adds a new Task", description = "Adds a new task in database")
    @PostMapping("/")
    public ResponseEntity<Void> createTask(@Valid @RequestBody TaskDtoRequest newTask, UriComponentsBuilder ucb) {
        Task savedTask = taskService.createTask(newTask);
        URI locationOfNewTask = ucb
                .path("tasks/{id}")
                .buildAndExpand(savedTask.getId())
                .toUri();
        return ResponseEntity.created(locationOfNewTask).build();
    }

    @Operation(summary = "Gets task by ID", description = "Returns a task with the requested id")
    @GetMapping("/{id}")
    public ResponseEntity<Object> findById(@PathVariable Long id) {
//        User user = getAuthUser();
        Optional<Task> taskOptional = taskService.getById(id);
        if (taskOptional.isEmpty()) {
            throw new NotFoundException("Task was not found");
        }
        return ResponseEntity.ok(taskOptional.get());
    }

    @Operation(summary = "Get all users task", description = "Returns all  task with the requested id")
//    @PreAuthorize("hasRole('USER')")
    @GetMapping("/user")
    public ResponseEntity<Object> getAllUsersTasks() {
//        User user = getAuthUser();
        List<TaskDto> taskDto = taskService.getAllUsersTasks();

        return ResponseEntity.ok(taskDto);
    }

    @Operation(summary = "Gets all tasks", description = "Returns all existing tasks")
//    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/all")
    protected ResponseEntity<List<TaskDto>> all() {

        List<TaskDto> taskDto = taskService.getAll();
        return ResponseEntity.ok(taskDto);
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    @Operation(summary = "Change task", description = "Change only task`s data (text), if task exists")
    @PutMapping("/{id}")
    public ResponseEntity<TaskDto> put(@PathVariable Long id, @Valid @RequestBody TaskDtoRequest taskDto) {
//        User user = getAuthUser();
        taskService.updateTask(id, taskDto);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Cansel task", description = "Can cansel task")
    @PatchMapping("/{id}/cancel")
    private ResponseEntity<Void> cansel(@PathVariable Long id) {
//        User user = getAuthUser();
        taskService.canselTask(id);
        return ResponseEntity.noContent().build();
    }

    @Transactional
    @Operation(summary = "Deleted Task", description = "Delete task from database")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
//        User user = getAuthUser();
        taskService.delete(id);
        return ResponseEntity.noContent().build();
    }

//    public ResponseEntity<?> authenticateUser(@RequestBody AuthenticationRequest request) {
//        String token = String.valueOf(authService.authenticate(request));
//        if (token != null) {
//            return ResponseEntity.ok().body(new AuthenticationResponse(token));
//        } else {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
//        }
//    }

//    private User getAuthUser() {
//        String username = SecurityContextHolder.getContext().getAuthentication().getName();
//        return userRepository.findUserByUsername(username).orElseThrow(() ->
//                new UsernameNotFoundException("Current user not found or not authorized"));
//    }

}
