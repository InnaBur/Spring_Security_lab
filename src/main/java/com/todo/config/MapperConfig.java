package com.todo.config;

import ch.qos.logback.core.model.ComponentModel;
import com.todo.mapper.TodoMapper;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.MappingConstants;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@org.mapstruct.MapperConfig(componentModel = MappingConstants.ComponentModel.SPRING,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR, nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    public interface MapperConfig {

}
