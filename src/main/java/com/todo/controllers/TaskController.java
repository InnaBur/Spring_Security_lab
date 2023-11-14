package com.todo.controllers;

import com.todo.dto.TaskDto;
import com.todo.entities.Task;
import com.todo.exceptions.NotFoundException;
import com.todo.services.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("tasks")
public class TaskController {


    TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
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

    @Operation(summary = "Gets all tasks", description = "Returns all existing tasks")
    @GetMapping
    protected ResponseEntity<List<TaskDto>> all() {
        List<TaskDto> taskDto = taskService.getAll();
        return ResponseEntity.ok(taskDto);
    }

    @Operation(summary = "Adds a new Task", description = "Adds a new task in database")
    @PostMapping
    private ResponseEntity<Void> createPerson(@Valid @RequestBody TaskDto newTask, UriComponentsBuilder ucb) {
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

}
