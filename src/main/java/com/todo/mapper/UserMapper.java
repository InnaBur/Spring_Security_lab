package com.todo.mapper;

import com.todo.config.MapperConfig;
import com.todo.dto.UserDTO;
import com.todo.entities.User;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface UserMapper {
    UserDTO userToUserDto(User user);
    User dtoToUserEntity(UserDTO userDto);
}
