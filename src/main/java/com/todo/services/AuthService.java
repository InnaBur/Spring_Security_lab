package com.todo.services;

import com.todo.dto.AuthenticationRequest;
import com.todo.dto.AuthenticationResponse;
import com.todo.exceptions.CustomValidateException;
import com.todo.lokalise.LanguageDefiner;
import com.todo.security.JwtService;
import com.todo.dto.UserRegisterRequestDTO;
import com.todo.entities.User;
import com.todo.enums.Roles;
import com.todo.exceptions.NotFoundException;
import com.todo.repository.UserRepository;
import jakarta.xml.bind.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public String register(UserRegisterRequestDTO request) {
        if (request.password().length() < 2) {
            throw new CustomValidateException(LanguageDefiner.toLocale("user.password.invalid"));
        }
        var user = User.builder()
                .username(request.username())
                .password(passwordEncoder.encode(request.password()))
                .build();
        user.setRole(Collections.singleton(Roles.USER));

        repository.save(user);
        return LanguageDefiner.toLocale( "user.register");

    }

//    public AuthenticationResponse register(UserRegisterRequestDTO request) {
//        var user = User.builder()
//                .username(request.username())
//                .password(passwordEncoder.encode(request.password()))
//                .build();
//        user.setRole(Collections.singleton(Roles.USER));
//
//        repository.save(user);
//        var jwtToken = jwtService.generateToken(user.getUsername());
//        return AuthenticationResponse.builder()
//                .token(jwtToken)
//                .build();
//
//    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        var user = repository.findUserByUsername(request.getUsername())
                .orElseThrow(() -> new NotFoundException("user.not.found"));
        var jwtToken = jwtService.generateToken(user.getUsername());
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();

    }

}
