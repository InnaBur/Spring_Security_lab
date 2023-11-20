package com.todo.services;

import com.todo.config.AuthenticationRequest;
import com.todo.config.AuthenticationResponse;
import com.todo.config.JwtService;
import com.todo.dto.UserRegisterRequestDTO;
import com.todo.entities.User;
import com.todo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(UserRegisterRequestDTO request) {
        var user = User.builder()
                .username(request.username())
                .password(passwordEncoder.encode(request.password()))
                .build();
        repository.save(user);
        var jwtToken = jwtService.generateToken(user.getUsername());         //username
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();

    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        var user = repository.findByUsername(request.getUsername())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user.getUsername());
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();

    }

    public String getUserToken(AuthenticationRequest request) {
        return  authenticate(request).getToken();

    }
}
