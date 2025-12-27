package com.utilitybilling.authservice.model;

import java.time.Instant;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection="users")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User{

    @Id
    private String id;

    @NotBlank
    private String username;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String password;

    private Role role;

    private boolean active=true;

    private Instant createdAt;
}
