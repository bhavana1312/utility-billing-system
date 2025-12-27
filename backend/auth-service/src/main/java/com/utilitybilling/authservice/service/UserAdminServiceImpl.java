package com.utilitybilling.authservice.service;

import com.utilitybilling.authservice.exception.InvalidCredentialsException;
import com.utilitybilling.authservice.model.User;
import com.utilitybilling.authservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserAdminServiceImpl implements UserAdminService{

    private final UserRepository userRepository;

    @Override
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    @Override
    public User getUserById(String userId){
        return userRepository.findById(userId)
                .orElseThrow(()->new InvalidCredentialsException("User not found"));
    }

    @Override
    public void deactivateUser(String userId){
        User user=getUserById(userId);
        user.setActive(false);
        userRepository.save(user);
    }
}
