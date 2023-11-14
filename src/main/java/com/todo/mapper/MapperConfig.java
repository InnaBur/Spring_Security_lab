package com.todo.mapper;

import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
    public class MapperConfig {

        @Bean
        public TodoMapper todoMapper() {
            return Mappers.getMapper(TodoMapper.class);
        }
}
