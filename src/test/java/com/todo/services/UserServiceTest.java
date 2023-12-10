package com.todo.services;

import com.todo.dto.UserDTO;
import com.todo.entities.User;
import com.todo.enums.Roles;
import com.todo.mapper.UserMapper;
import com.todo.mapper.UserMapperImpl;
import com.todo.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@TestPropertySource(locations = "classpath:test.properties")
class UserServiceTest {


    private final UserRepository userRepository = mock(UserRepository.class);

    private final UserMapper userMapper = mock(UserMapper.class);
    UserService userService = new UserService(userRepository, userMapper);

    @Test
    void getUserByIdTest() {
        User user = new User(1L, "Inna", "Burlaka", Set.of(Roles.USER));
        Long id = 1L;
        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        Optional<User> result = userService.getUserById(id);

        assertTrue(result.isPresent());
        assertEquals("Inna", result.get().getUsername());

        verify(userRepository, times(1)).findById(any());

    }

    //    @Test
//    void findByIdTest() {
//        Person person = new Person(10L, "1", "Name", "P");
//
//        when(personRepository.findById(10L)).thenReturn(Optional.of(person));
//        ResponseEntity<Object> response = personService.findById(person.getId());
//
//        assertEquals(200, response.getStatusCode().value());
//        verify(personRepository, times(1)).findById(10L);
//
//        Person gotPerson = (Person) response.getBody();
//        assertEquals("Name", gotPerson.getName());
//
//    }
//
//    @Test
//    void findByIdTestException() {
//
//        when(personRepository.findById(1L)).thenReturn(Optional.empty());
//
//        assertThrows(PersonNotFoundException.class, () -> personService.findById(1L));
//    }
//
    @Test
    void getAllUsers() {
        User user = new User(1L, "1", "1", Set.of(Roles.USER));
        User user1 = new User(2L, "2", "2", Set.of(Roles.USER));
        User user2 = new User(3L, "3", "3", Set.of(Roles.USER));
        List<User> users = List.of(user, user1, user2);
        when(userRepository.findAll()).thenReturn(users);
        UserMapper userMapper1 = new UserMapperImpl();
        List<UserDTO> results = users.stream()
                .map(userMapper1::userToUserDto)
                .toList();

        UserDTO userDTO = userMapper1.userToUserDto(user);
        userService.getAllUsers();
        assertEquals(3, results.size());
        assertEquals(userDTO.getRole(), results.get(0).getRole());
    }

}