package com.utilitybilling.authservice.controller;

import com.utilitybilling.authservice.dto.ChangePasswordRequest;
import com.utilitybilling.authservice.dto.ForgotPasswordRequest;
import com.utilitybilling.authservice.dto.LoginRequest;
import com.utilitybilling.authservice.dto.LoginResponse;
import com.utilitybilling.authservice.dto.RegisterRequest;
import com.utilitybilling.authservice.dto.RegisterResponse;
import com.utilitybilling.authservice.dto.ResetPasswordRequest;
import com.utilitybilling.authservice.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

	private final AuthService authService;

	@PostMapping("/register")
	public ResponseEntity<RegisterResponse> register(@Valid @RequestBody RegisterRequest request) {
		return ResponseEntity.status(HttpStatus.CREATED).body(authService.register(request));
	}

	@PostMapping("/login")
	public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
		return ResponseEntity.ok(authService.login(request));
	}
	
	@PostMapping("/forgot-password")
	public ResponseEntity<?> forgotPassword(
	        @Valid @RequestBody ForgotPasswordRequest request){
	    authService.forgotPassword(request);
	    return ResponseEntity.ok(
	            Map.of("message","Password reset link sent to registered email"));
	}

	@PostMapping("/reset-password")
	public ResponseEntity<?> resetPassword(
	        @Valid @RequestBody ResetPasswordRequest request){
	    authService.resetPassword(request);
	    return ResponseEntity.ok(
	            Map.of("message","Password reset successful"));
	}
	
	@PutMapping("/change-password")
	public ResponseEntity<?> changePassword(
	        @AuthenticationPrincipal org.springframework.security.core.userdetails.UserDetails userDetails,
	        @Valid @RequestBody ChangePasswordRequest request){

	    authService.changePassword(userDetails.getUsername(),request);
	    return ResponseEntity.ok(
	            Map.of("message","Password updated successfully"));
	}

}
