package com.utilitybilling.authservice.service;

import java.util.List;

import com.utilitybilling.authservice.dto.UserResponse;

public interface UserAdminService{
    List<UserResponse> getAllUsers();
    UserResponse getUserById(String userId);
    void deactivateUser(String userId);
}
