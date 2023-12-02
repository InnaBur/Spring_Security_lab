package com.todo.services;

import com.todo.entities.User;
import com.todo.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username)  {
        return userRepository.findUserByUsername(username)
                .map(user -> new User(user.getUserId(), user.getUsername(), user.getPassword(), user.getRole()))
                .orElseThrow(()-> new UsernameNotFoundException(""));
    }
}


