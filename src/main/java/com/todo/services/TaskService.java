package com.todo.services;

import com.todo.TaskStatus;
import com.todo.dto.TaskDto;
import com.todo.entities.Task;
import com.todo.exceptions.ImmutableException;
import com.todo.exceptions.NotFoundException;
import com.todo.mapper.TodoMapper;
import com.todo.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final TodoMapper todoMapper;

    public TaskService(TaskRepository taskRepository, TodoMapper todoMapper) {
        this.taskRepository = taskRepository;
        this.todoMapper = todoMapper;
    }

    public Task createTask(TaskDto taskDto) {
        return taskRepository.save(todoMapper.dtoToEntity(taskDto));
    }

    public List<TaskDto> getAll() {
        List<Task> tasks = taskRepository.findAll();
        return tasks.stream()
                .map(todoMapper::taskToTaskDto)
                .toList();
    }

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
}
