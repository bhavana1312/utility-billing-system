package com.utilitybilling.authservice.config;

import java.time.Instant;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.utilitybilling.authservice.model.Role;
import com.utilitybilling.authservice.model.User;
import com.utilitybilling.authservice.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class AdminSeeder{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Bean
    public CommandLineRunner seedAdmin(){
        return args -> {
            if(!userRepository.existsByUsername("admin")){
                User admin=User.builder()
                    .username("admin")
                    .email("admin@utilitybilling.com")
                    .password(passwordEncoder.encode("Admin@123"))
                    .role(Role.ADMIN)
                    .active(true)
                    .createdAt(Instant.now())
                    .build();
                userRepository.save(admin);
            }
        };
    }
}
