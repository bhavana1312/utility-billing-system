package com.utilitybilling.authservice.service;

import com.utilitybilling.authservice.dto.ChangePasswordRequest;
import com.utilitybilling.authservice.dto.ForgotPasswordRequest;
import com.utilitybilling.authservice.dto.LoginRequest;
import com.utilitybilling.authservice.dto.LoginResponse;
import com.utilitybilling.authservice.dto.RegisterRequest;
import com.utilitybilling.authservice.dto.RegisterResponse;
import com.utilitybilling.authservice.dto.ResetPasswordRequest;

public interface AuthService{
    RegisterResponse register(RegisterRequest request);
    LoginResponse login(LoginRequest request);
    void forgotPassword(ForgotPasswordRequest request);
    void resetPassword(ResetPasswordRequest request);
    void changePassword(String username,ChangePasswordRequest request);
}
