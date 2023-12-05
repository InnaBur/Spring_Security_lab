package com.todo.controllers;

import com.todo.dto.TaskDto;
import com.todo.dto.TaskDtoRequest;
import com.todo.dto.TaskDtoResponse;
import com.todo.entities.Task;
import com.todo.entities.User;
import com.todo.exceptions.NotFoundException;
import com.todo.lokalise.LanguageDefiner;
import com.todo.mapper.TodoMapper;
import com.todo.repository.UserRepository;
import com.todo.services.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
public class TaskController {

    TaskService taskService;
    UserRepository userRepository;
    TodoMapper todoMapper;

    public TaskController(TaskService taskService, UserRepository userRepository, TodoMapper todoMapper) {
        this.taskService = taskService;
        this.userRepository = userRepository;
        this.todoMapper = todoMapper;
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    @Operation(summary = "Adds a new Task", description = "Adds a new task in database")
    @PostMapping("/")
    public ResponseEntity<TaskDtoResponse> createTask(@Valid @RequestBody TaskDtoRequest newTask, UriComponentsBuilder ucb) {
        User user = getAuthUser();
        Task savedTask = taskService.createTask(newTask, user);
        URI locationOfNewTask = ucb
                .path("tasks/{id}")
                .buildAndExpand(savedTask.getId())
                .toUri();
        return ResponseEntity.created(locationOfNewTask).body(todoMapper.taskToTaskDtoResponse(savedTask));
    }

    @Operation(summary = "Gets task by ID", description = "Returns a task with the requested id")
    @GetMapping("/{id}")
    public ResponseEntity<Object> findById(@PathVariable Long id) {
        User user = getAuthUser();
        Optional<Task> taskOptional = taskService.getById(id, user);
        if (taskOptional.isEmpty()) {
            throw new NotFoundException(LanguageDefiner.toLocale("exception.task.not.found"));
        }
        return ResponseEntity.ok(taskOptional.get());
    }

    @Operation(summary = "Get all users task", description = "Returns all  task with the requested id")
    @GetMapping("/user")
    public ResponseEntity<Object> getAllUsersTasks() {
        User user = getAuthUser();
        List<TaskDto> taskDto = taskService.getAllUsersTasks(user);

        return ResponseEntity.ok(taskDto);
    }

    @Operation(summary = "Gets all tasks", description = "Returns all existing tasks")
    @GetMapping("/all")
    protected ResponseEntity<List<TaskDto>> all() {
        List<TaskDto> taskDto = taskService.getAll();
        return ResponseEntity.ok(taskDto);
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    @Operation(summary = "Change task", description = "Change only task`s data (text), if task exists")
    @PutMapping("/{id}")
    public ResponseEntity<TaskDto> put(@PathVariable Long id, @Valid @RequestBody TaskDtoRequest taskDto) {
        User user = getAuthUser();
        Task task = taskService.updateTask(id, taskDto, user);
        return ResponseEntity.status(HttpStatus.CREATED).body(todoMapper.taskToTaskDto(task));
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    @Operation(summary = "Cansel task", description = "Can cansel task")
    @PatchMapping("/{id}/cancel")
    public ResponseEntity<Void> cansel(@PathVariable Long id) {
        User user = getAuthUser();
        taskService.canselTask(id, user);
        return ResponseEntity.noContent().build();
    }

    @Transactional
    @Operation(summary = "Deleted Task", description = "Delete task from database")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        User user = getAuthUser();
        taskService.delete(id, user);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).header("Message", LanguageDefiner.toLocale("task.deleted", id.toString())).build();
//                body("The task with ID " + id + " successfully deleted");
//        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("info.task.deleted %l" + id);
    }

    private User getAuthUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findUserByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException("exception.user.not.found"));
    }

}
