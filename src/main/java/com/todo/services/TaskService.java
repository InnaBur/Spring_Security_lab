package com.todo.services;

import com.todo.dto.TaskDtoRequest;
import com.todo.dto.TaskDtoResponse;
import com.todo.entities.User;
import com.todo.enums.TaskStatus;
import com.todo.dto.TaskDto;
import com.todo.entities.Task;
import com.todo.exceptions.ImmutableException;
import com.todo.exceptions.NotFoundException;
import com.todo.lokalise.LanguageDefiner;
import com.todo.mapper.TodoMapper;
import com.todo.repository.TaskRepository;
import com.todo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

//@Service
@Component
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final  TodoMapper todoMapper;


    public Task createTask(TaskDtoRequest taskDto, User user) {
        Task task = todoMapper.dtoRequestToEntity(taskDto);
        task.setUser(user);
//        task.setLocalizedTaskStatus(taskDto.getTaskStatus().getMessageKey());
        return taskRepository.save(task);
    }

    public List<TaskDto> getAllUsersTasks(User user) {
        List<Task> tasks =  taskRepository.findAllByUser(user);
        return tasks.stream()
                .map(todoMapper::taskToTaskDto)
                .toList();
    }



    public List<TaskDto> getAll() {
        List<Task> tasks = taskRepository.findAll();
        return tasks.stream()
                .map(todoMapper::taskToTaskDto)
                .toList();
    }

    public Optional<Task> getById(Long id, User user) {
        return taskRepository.findByUserAndId(user, id);
    }

    public Task updateTask(Long id, TaskDtoRequest taskDto, User user) {
        Task existingTask = getTaskIfExist(user, id);

        Task toUpdate = todoMapper.dtoRequestToEntity(taskDto);
        existingTask.setTask(toUpdate.getTask());
        existingTask.setTaskStatus(changeStatus(taskDto, existingTask));
        existingTask.setTime(LocalDateTime.now());
        taskRepository.save(existingTask);
        return existingTask;
    }

    public void canselTask(Long id, User user) {
        Task existingTask = getTaskIfExist(user, id);
        throwImmutableException(existingTask);
        existingTask.setTaskStatus(TaskStatus.CANCELED);
        taskRepository.save(existingTask);
    }

    public void delete(Long id, User user) {
        if (!taskRepository.existsByIdAndUser(id, user)) {
            throw new NotFoundException(LanguageDefiner.toLocale("exception.task.not.found"));
        }
        taskRepository.deleteByIdAndUser(id, user);
    }

    private void throwImmutableException(Task existingTask) {
        if (existingTask.getTaskStatus().immutableStatus()) {
            throw new ImmutableException(LanguageDefiner.toLocale(
                    "exception.immutable.status", existingTask.getTaskStatus().toString()));
        }
    }

    private Task getTaskIfExist(User user, Long id) {
        Optional<Task> taskForUpdate = taskRepository.findByUserAndId(user, id);
        if (taskForUpdate.isEmpty()) {
            throw new NotFoundException("exception.task.not.found");
        }
        return taskForUpdate.get();
    }

    public TaskStatus changeStatus(TaskDtoRequest taskDtoRequest, Task existingTask) {

        TaskStatus status = taskDtoRequest.getTaskStatus();

        TaskStatus existingTaskStatus = existingTask.getTaskStatus();

        if (status.equals(existingTaskStatus) || existingTaskStatus.nextStatus().contains(status)) {
            return status;
        } else {
            throwImmutableException(existingTask);
            return existingTaskStatus;
        }
    }
}
