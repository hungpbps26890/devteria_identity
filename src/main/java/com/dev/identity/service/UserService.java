package com.dev.identity.service;

import com.dev.identity.dto.request.UserCreationRequest;
import com.dev.identity.dto.request.UserUpdateRequest;
import com.dev.identity.dto.response.UserResponse;
import com.dev.identity.entity.User;

import java.util.List;

public interface UserService {

    UserResponse createUser(UserCreationRequest request);

    List<UserResponse> getUsers();

    UserResponse getUserById(String id);

    UserResponse updateUser(String id, UserUpdateRequest request);

    void deleteUserById(String id);
}
