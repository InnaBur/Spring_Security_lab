package com.todo.controllers;

import com.todo.services.AuthService;
import com.todo.config.AuthenticationRequest;
import com.todo.config.AuthenticationResponse;
import com.todo.dto.TaskDto;
import com.todo.entities.Task;
import com.todo.entities.User;
import com.todo.exceptions.NotFoundException;
import com.todo.repository.TaskRepository;
import com.todo.repository.UserRepository;
import com.todo.services.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("tasks")
@SecurityRequirement(name = "ToDoAPI")
public class TaskController {


    TaskService taskService;
    AuthService authService;

    UserRepository userRepository;

    TaskRepository taskRepository;

    public TaskController(TaskService taskService, UserRepository userRepository) {
        this.taskService = taskService;
        this.userRepository = userRepository;
    }

    @Operation(summary = "Gets task by ID", description = "Returns a task with the requested id")
    @GetMapping("/{id}")
    public ResponseEntity<Object> findById(@PathVariable Long id) {

        Optional<Task> taskOptional = taskService.getById(id);
        if (taskOptional.isEmpty()) {
            throw new NotFoundException("Task was not found");
        }
        return ResponseEntity.ok(taskOptional.get());
    }

    @Operation(summary = "Get all users task", description = "Returns all  task with the requested id")
    @PreAuthorize("hasRole('USER')")
    @GetMapping
    public ResponseEntity<Object> getAllUsersTasks() {

        List<TaskDto> taskDto = taskService.getAllUsersTasks();

        return ResponseEntity.ok(taskDto);
    }

    @Operation(summary = "Gets all tasks", description = "Returns all existing tasks")

    @GetMapping("/{user_id}")
    protected ResponseEntity<List<TaskDto>> all() {

        List<TaskDto> taskDto = taskService.getAll();
        return ResponseEntity.ok(taskDto);
    }

//    @Operation(summary = "Gets all tasks", description = "Returns all existing tasks")
//    @GetMapping("/{user_id}/tasks")
//    protected ResponseEntity<List<TaskDto>> allUsersTasks(Authentication authentication) {
//        String username = authentication.getName();
//        if (username != null) {
//            List<TaskDto> taskDto = taskService.getAllByUser(username);
//            return ResponseEntity.ok(taskDto);
//        }
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
//    }

    //    @Operation(summary = "Gets all tasks", description = "Returns all existing tasks")
//    @GetMapping
//    protected ResponseEntity<List<TaskDto>> all() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        List<TaskDto> taskDto = taskService.getAll();
//        return ResponseEntity.ok(taskDto.findByOwner(authentication.getName()));
//    }
    @Operation(summary = "Adds a new Task", description = "Adds a new task in database")
    @PreAuthorize("hasRole('USER')")
    @PostMapping()
    private ResponseEntity<Void> createTask(@Valid @RequestBody TaskDto newTask, UriComponentsBuilder ucb) {
        Task savedTask = taskService.createTask(newTask);
        URI locationOfNewTask = ucb
                .path("tasks/{id}")
                .buildAndExpand(savedTask.getId())
                .toUri();
        return ResponseEntity.created(locationOfNewTask).build();
    }

    @Operation(summary = "Change task", description = "Change only task`s data (text), if task exists")
    @PutMapping("/{id}")
    private ResponseEntity<Void> put(@PathVariable Long id, @Valid @RequestBody TaskDto taskDto) {
        taskService.updateTask(id, taskDto);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Cansel task", description = "Can cansel task")
    @PatchMapping("/{id}/cancel")
    private ResponseEntity<Void> cansel(@PathVariable Long id) {
        taskService.canselTask(id);
        return ResponseEntity.noContent().build();
    }


    @Operation(summary = "Update status", description = "Automatically updates task status")
    @PatchMapping("/{id}/change")
    private ResponseEntity<Void> change(@PathVariable Long id) {
        taskService.updateStatus(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Deleted Task", description = "Delete task from database")
    @DeleteMapping("/{id}")
    private ResponseEntity<Void> delete(@PathVariable Long id) {
        taskService.delete(id);
        return ResponseEntity.noContent().build();
    }

    public ResponseEntity<?> authenticateUser(@RequestBody AuthenticationRequest request) {
        String token = String.valueOf(authService.authenticate(request));
        if (token != null) {
            return ResponseEntity.ok().body(new AuthenticationResponse(token));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

}
