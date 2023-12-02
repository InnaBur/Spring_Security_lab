package com.todo.mapper;

import com.todo.config.MapperConfig;
import com.todo.dto.TaskDto;
import com.todo.dto.TaskDtoRequest;
import com.todo.dto.TaskDtoResponse;
import com.todo.entities.Task;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

@Mapper(config = MapperConfig.class)
public interface TodoMapper {
        TaskDto taskToTaskDto(Task task);
        Task dtoToEntity(TaskDto taskDto);

        TaskDtoRequest taskToTaskDtoRequest(Task task);
        Task dtoRequestToEntity(TaskDtoRequest taskDto);

        TaskDtoResponse taskToTaskDtoResponse(Task task);
        Task dtoResponseToEntity(TaskDtoResponse taskDto);

}
