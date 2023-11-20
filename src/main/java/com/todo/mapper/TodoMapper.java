package com.todo.mapper;

import com.todo.config.MapperConfig;
import com.todo.dto.TaskDto;
import com.todo.entities.Task;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

@Mapper(config = MapperConfig.class)
public interface TodoMapper {
        TaskDto taskToTaskDto(Task task);
        Task dtoToEntity(TaskDto taskDto);

}
