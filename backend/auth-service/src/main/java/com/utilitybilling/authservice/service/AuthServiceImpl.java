package com.utilitybilling.authservice.service;

import com.utilitybilling.authservice.dto.RegisterRequest;
import com.utilitybilling.authservice.dto.RegisterResponse;
import com.utilitybilling.authservice.exception.DuplicateUserException;
import com.utilitybilling.authservice.model.Role;
import com.utilitybilling.authservice.model.User;
import com.utilitybilling.authservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public RegisterResponse register(RegisterRequest request){

        if(userRepository.existsByUsername(request.getUsername()))
            throw new DuplicateUserException("Username already exists");

        if(userRepository.existsByEmail(request.getEmail()))
            throw new DuplicateUserException("Email already exists");

        User user=User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.valueOf(request.getRole()))
                .active(true)
                .createdAt(Instant.now())
                .build();

        User saved=userRepository.save(user);

        return new RegisterResponse(saved.getId(),"User registered successfully");
    }
}
