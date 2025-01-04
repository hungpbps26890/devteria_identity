package com.dev.identity.controller;

import com.dev.identity.dto.request.UserCreationRequest;
import com.dev.identity.dto.request.UserUpdateRequest;
import com.dev.identity.dto.response.ApiResponse;
import com.dev.identity.dto.response.UserResponse;
import com.dev.identity.entity.User;
import com.dev.identity.service.UserService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {

    UserService userService;

    @PostMapping
    public ResponseEntity<ApiResponse<UserResponse>> createUser(@Valid @RequestBody UserCreationRequest request) {

        return new ResponseEntity<>(
                ApiResponse.<UserResponse>builder()
                        .code(HttpStatus.CREATED.value())
                        .message("User created successfully")
                        .data(userService.createUser(request))
                        .build(),
                HttpStatus.CREATED);
    }

    @GetMapping
    public ApiResponse<List<UserResponse>> getUsers() {
        return ApiResponse.<List<UserResponse>>builder()
                .code(HttpStatus.OK.value())
                .message("Get list of users successfully")
                .data(userService.getUsers())
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<UserResponse> getUserById(@PathVariable("id") String id) {
        return ApiResponse.<UserResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Get user successfully")
                .data(userService.getUserById(id))
                .build();
    }

    @GetMapping("/my-info")
    public ApiResponse<UserResponse> getMyInfo() {
        return ApiResponse.<UserResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Get my info successfully")
                .data(userService.getMyInfo())
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<UserResponse> updateUser(
            @PathVariable("id") String id,
            @RequestBody UserUpdateRequest request
    ) {
        return ApiResponse.<UserResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Update user successfully")
                .data(userService.updateUser(id, request))
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteUserById(@PathVariable("id") String id) {
        userService.deleteUserById(id);

        return ApiResponse.<Void>builder()
                .code(HttpStatus.OK.value())
                .message("User deleted successfully")
                .build();
    }
}
