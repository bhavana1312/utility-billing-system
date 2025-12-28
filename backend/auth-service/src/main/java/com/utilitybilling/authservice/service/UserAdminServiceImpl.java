package com.utilitybilling.authservice.service;

import com.utilitybilling.authservice.dto.UserResponse;
import com.utilitybilling.authservice.exception.InvalidCredentialsException;
import com.utilitybilling.authservice.model.User;
import com.utilitybilling.authservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserAdminServiceImpl implements UserAdminService{

    private final UserRepository userRepository;

    @Override
    public List<UserResponse> getAllUsers(){
        return userRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserResponse getUserById(String userId){
        User user=userRepository.findById(userId)
                .orElseThrow(()->new InvalidCredentialsException("User not found"));
        return mapToDto(user);
    }


    @Override
    public void deactivateUser(String userId){
        User user=userRepository.findById(userId)
                .orElseThrow(()->new InvalidCredentialsException("User not found"));
        user.setActive(false);
        userRepository.save(user);
    }
    
    private UserResponse mapToDto(User user){
        return new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getRole().name(),
                user.isActive()
        );
    }
}
