package com.todo.services;

import com.todo.dto.TaskDto;
import com.todo.dto.TaskDtoRequest;
import com.todo.dto.UserDTO;
import com.todo.entities.Task;
import com.todo.entities.User;
import com.todo.enums.Roles;
import com.todo.enums.TaskStatus;
import com.todo.exceptions.ImmutableException;
import com.todo.exceptions.NotFoundException;
import com.todo.lokalise.LanguageDefiner;
import com.todo.mapper.TodoMapper;
import com.todo.mapper.TodoMapperImpl;
import com.todo.mapper.UserMapper;
import com.todo.mapper.UserMapperImpl;
import com.todo.repository.TaskRepository;
import lombok.Data;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class TaskServiceTest {

    private final TaskRepository taskRepository = mock(TaskRepository.class);
    private final TodoMapper todoMapper = mock(TodoMapper.class);

    TaskService taskService = new TaskService(taskRepository, todoMapper);
    @Test
    void createTaskTest() {
        Task task = new Task("task");
        User user = new User(1, "1", "1", Set.of(Roles.USER));
        TaskDtoRequest taskDto = new TaskDtoRequest();
        taskDto.setTask("task");
        when(todoMapper.dtoRequestToEntity(any())).thenReturn(task);
        task.setUser(user);
        when(taskRepository.save(task)).thenReturn(task);

        taskService.createTask(taskDto, user);

        verify(taskRepository, times(1)).save(any());

    }

    @Test
    void getAllUsersTasksTest() {

        User user = new User(1L, "1", "1", Set.of(Roles.USER));
        Task task = new Task("first");
        Task task1 = new Task("second");
        List<Task> tasks = List.of(task, task1);
        when(taskRepository.findAllByUser(user)).thenReturn(tasks);
        TodoMapper mapper = new TodoMapperImpl();
        List<TaskDto> results = tasks.stream()
                .map(mapper::taskToTaskDto)
                .toList();

        taskService.getAllUsersTasks(user);

        assertEquals(2, results.size());
        assertEquals("first", tasks.get(0).getTask());
    }

    @Test
    void getAll() {
    }

    @Test
    void getById() {
    }

    @Test
    void updateTaskTest() {
        Task existingTask = new Task("first");
        User user = new User(1L, "1", "1", Set.of(Roles.USER));
          when(taskRepository.findByUserAndId(user, 1L)).thenReturn(Optional.of(existingTask));
//        TaskDto toUpdate = new TaskDto();
        Task toUpdate = new Task();
        toUpdate.setTask("update");
        toUpdate.setUser(user);
        when(todoMapper.dtoRequestToEntity(any())).thenReturn(toUpdate);
        when(taskRepository.save(any())).thenReturn(existingTask);
        TodoMapper mapper = new TodoMapperImpl();
        taskService.updateTask(1L, mapper.taskToTaskDtoRequest(toUpdate), user);

        assertEquals("update", toUpdate.getTask());
    }

    @Test
    void canselTask() {
    }

    @Test
    void delete() {
    }

//    @Test
//    void changeStatusTest() {
//        LanguageDefiner languageDefinerMock = mock(LanguageDefiner.class);
//
//        when(languageDefinerMock.toLocale(anyString(), any())).thenReturn("dfdgsdg");
//
//        TaskDtoRequest taskDtoRequest = new TaskDtoRequest();
//        taskDtoRequest.setTaskStatus(TaskStatus.CANCELED);
//
//        Task task = new Task();
//        task.setTaskStatus(TaskStatus.DONE);
//
////        taskService.changeStatus(taskDtoRequest, task);
//
//        assertThrows(ImmutableException.class, () -> taskService.changeStatus(taskDtoRequest, task));
//        TaskStatus status = taskDtoRequest.getTaskStatus();
//
//        TaskStatus existingTaskStatus = existingTask.getTaskStatus();
//
//        if (status.equals(existingTaskStatus) || existingTaskStatus.nextStatus().contains(status)) {
//            return status;
//        } else {
//            throwImmutableException(existingTask);
//            return existingTaskStatus;
//        }
//    }

}