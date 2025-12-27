package com.utilitybilling.authservice.service;

import com.utilitybilling.authservice.dto.RegisterRequest;
import com.utilitybilling.authservice.dto.RegisterResponse;

public interface AuthService{
    RegisterResponse register(RegisterRequest request);
}
