package com.utilitybilling.authservice.service;

import com.utilitybilling.authservice.dto.ForgotPasswordRequest;
import com.utilitybilling.authservice.dto.LoginRequest;
import com.utilitybilling.authservice.dto.LoginResponse;
import com.utilitybilling.authservice.dto.RegisterRequest;
import com.utilitybilling.authservice.dto.RegisterResponse;
import com.utilitybilling.authservice.dto.ResetPasswordRequest;
import com.utilitybilling.authservice.exception.DuplicateUserException;
import com.utilitybilling.authservice.exception.InvalidCredentialsException;
import com.utilitybilling.authservice.exception.InvalidResetTokenException;
import com.utilitybilling.authservice.model.PasswordResetToken;
import com.utilitybilling.authservice.model.Role;
import com.utilitybilling.authservice.model.User;
import com.utilitybilling.authservice.repository.PasswordResetTokenRepository;
import com.utilitybilling.authservice.repository.UserRepository;
import com.utilitybilling.authservice.security.JwtUtil;

import java.time.Duration;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final AuthenticationManager authenticationManager;
	private final JwtUtil jwtUtil;
	private final PasswordResetTokenRepository tokenRepository;


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
	
	@Override
	public void forgotPassword(ForgotPasswordRequest request){

	    User user=userRepository.findByEmail(request.getEmail())
	            .orElseThrow(()->new InvalidCredentialsException("User not found"));

	    tokenRepository.deleteByUserId(user.getId());

	    String token=UUID.randomUUID().toString();

	    PasswordResetToken resetToken=PasswordResetToken.builder()
	            .token(token)
	            .userId(user.getId())
	            .expiryTime(Instant.now().plus(Duration.ofMinutes(15)))
	            .build();

	    tokenRepository.save(resetToken);

	    // send token via Notification Service
	}
	
	@Override
	public void resetPassword(ResetPasswordRequest request){

	    PasswordResetToken resetToken=tokenRepository.findByToken(request.getResetToken())
	            .orElseThrow(()->new InvalidResetTokenException("Invalid reset token"));

	    if(resetToken.getExpiryTime().isBefore(Instant.now()))
	        throw new InvalidResetTokenException("Reset token expired");

	    User user=userRepository.findById(resetToken.getUserId())
	            .orElseThrow(()->new InvalidCredentialsException("User not found"));

	    user.setPassword(passwordEncoder.encode(request.getNewPassword()));
	    userRepository.save(user);

	    tokenRepository.deleteByUserId(user.getId());
	}

	
	

}
