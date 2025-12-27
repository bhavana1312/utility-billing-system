package com.utilitybilling.authservice.service;

import com.utilitybilling.authservice.model.User;
import java.util.List;

public interface UserAdminService{
    List<User> getAllUsers();
    User getUserById(String userId);
    void deactivateUser(String userId);
}
