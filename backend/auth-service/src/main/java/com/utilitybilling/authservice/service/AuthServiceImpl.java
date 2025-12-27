package com.utilitybilling.authservice.service;

import com.utilitybilling.authservice.dto.LoginRequest;
import com.utilitybilling.authservice.dto.LoginResponse;
import com.utilitybilling.authservice.dto.RegisterRequest;
import com.utilitybilling.authservice.dto.RegisterResponse;
import com.utilitybilling.authservice.exception.DuplicateUserException;
import com.utilitybilling.authservice.exception.InvalidCredentialsException;
import com.utilitybilling.authservice.model.Role;
import com.utilitybilling.authservice.model.User;
import com.utilitybilling.authservice.repository.UserRepository;
import com.utilitybilling.authservice.security.JwtUtil;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final AuthenticationManager authenticationManager;
	private final JwtUtil jwtUtil;

	@Override
	public RegisterResponse register(RegisterRequest request) {

		if (userRepository.existsByUsername(request.getUsername()))
			throw new DuplicateUserException("Username already exists");

		if (userRepository.existsByEmail(request.getEmail()))
			throw new DuplicateUserException("Email already exists");

		User user = User.builder().username(request.getUsername()).email(request.getEmail())
				.password(passwordEncoder.encode(request.getPassword())).role(Role.valueOf(request.getRole()))
				.active(true).createdAt(Instant.now()).build();

		User saved = userRepository.save(user);

		return new RegisterResponse(saved.getId(), "User registered successfully");
	}

	@Override
	public LoginResponse login(LoginRequest request) {
		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

			User user = userRepository.findByUsername(request.getUsername())
					.orElseThrow(() -> new InvalidCredentialsException("Invalid credentials"));

			String token = jwtUtil.generateToken(user.getUsername(), user.getRole().name());

			return new LoginResponse(token, user.getRole().name());

		} catch (AuthenticationException ex) {
			throw new InvalidCredentialsException("Invalid credentials");
		}
	}
}
