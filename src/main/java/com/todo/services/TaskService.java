package com.todo.services;

import com.todo.entities.User;
import com.todo.enums.TaskStatus;
import com.todo.dto.TaskDto;
import com.todo.entities.Task;
import com.todo.exceptions.ImmutableException;
import com.todo.exceptions.NotFoundException;
import com.todo.mapper.TodoMapper;
import com.todo.repository.TaskRepository;
import com.todo.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    TaskRepository taskRepository;
    UserRepository userRepository;
    TodoMapper todoMapper;

    public TaskService(TaskRepository taskRepository, UserRepository userRepository, TodoMapper todoMapper) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.todoMapper = todoMapper;
    }

    public Task createTask(TaskDto taskDto) {
        User user = getAuthUser();
        Task task = todoMapper.dtoToEntity(taskDto);
        task.setUser(user);
        return taskRepository.save(task);
    }

    public List<TaskDto> getAllUsersTasks() {
        User user = getAuthUser();
        List<Task> tasks =  taskRepository.findAllByUser(user);
        return tasks.stream()
                .map(todoMapper::taskToTaskDto)
                .toList();
    }

    private String getAuthUsername() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findUserByUsername(username).orElseThrow(() -> new NotFoundException("This user was not found"));
        return user.getUsername();
    }

    private User getAuthUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findUserByUsername(username).orElseThrow(() -> new NotFoundException("This user was not found"));
    }
    public List<TaskDto> getAll() {
        List<Task> tasks = taskRepository.findAll();
        return tasks.stream()
                .map(todoMapper::taskToTaskDto)
                .toList();
    }

//    public List<TaskDto> getAllTasksByUser(Long userId) {
//        validateUser(userId);
//
//        List<Task> tasks = taskRepository.findAll();
//        return tasks.stream()
//                .map(todoMapper::taskToTaskDto)
//                .toList();
//    }

    public Optional<Task> getById(Long id) {
        return taskRepository.findById(id);
    }

    public void updateTask(Long id, TaskDto taskDto) {

        Task existingTask = getTaskIfExist(id);

        Task toUpdate = todoMapper.dtoToEntity(taskDto);
        existingTask.setTask(toUpdate.getTask());
        existingTask.setTaskStatus(existingTask.getTaskStatus());
        existingTask.setTime(LocalDateTime.now());
        taskRepository.save(existingTask);
    }


    public void updateStatus(Long id) {

        Task existingTask = getTaskIfExist(id);

        throwImmutableException(existingTask);
        existingTask.setTaskStatus(existingTask.getTaskStatus().nextStatus());
        taskRepository.save(existingTask);
    }


    public void canselTask(Long id) {

        Task existingTask = getTaskIfExist(id);
        throwImmutableException(existingTask);
        existingTask.setTaskStatus(TaskStatus.CANCELED);
        taskRepository.save(existingTask);
    }

    public void delete(Long id) {
        if (!taskRepository.existsById(id)) {
            throw new NotFoundException("Task was not found");
        }
        taskRepository.deleteById(id);
    }

    private void throwImmutableException(Task existingTask) {
        if (existingTask.getTaskStatus().immutableStatus()) {
            throw new ImmutableException("Status " + existingTask.getTaskStatus() + " of this task is immutable");
        }
    }

    private Task getTaskIfExist(Long id) {
        Optional<Task> taskForUpdate = taskRepository.findById(id);
        if (taskForUpdate.isEmpty()) {
            throw new NotFoundException("Task was not found");
        }
        return taskForUpdate.get();
    }

//    private User validateUser(Long userId) {
//        return userRepository.findById(userId)
//                .orElseThrow(() -> new NotFoundException("User was not found"));
//    }
//
//    private Task validateTaskByUser(Long userId, Long taskId) {
//        return taskRepository.findOneTaskByUserId(taskId, userId)
//                .orElseThrow(() -> new NotFoundException("Task was not found"));
//    }

}
