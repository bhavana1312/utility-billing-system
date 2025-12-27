package com.utilitybilling.authservice.service;

import com.utilitybilling.authservice.dto.LoginRequest;
import com.utilitybilling.authservice.dto.LoginResponse;
import com.utilitybilling.authservice.dto.RegisterRequest;
import com.utilitybilling.authservice.dto.RegisterResponse;

public interface AuthService{
    RegisterResponse register(RegisterRequest request);
    LoginResponse login(LoginRequest request);
}
