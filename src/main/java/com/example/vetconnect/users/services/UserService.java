package com.example.vetconnect.users.services;

import com.example.vetconnect.users.dto.CreateUserRequest;
import com.example.vetconnect.users.dto.UpdateUserRequest;
import com.example.vetconnect.users.dto.UserResponse;

import java.util.List;

public interface UserService {
    List<UserResponse> getAllUsers();

    String createUser(CreateUserRequest request);

    UserResponse updateUser(Long id, UpdateUserRequest request);

    String deleteUser(Long id);

}
