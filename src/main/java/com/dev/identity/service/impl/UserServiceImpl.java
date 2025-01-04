package com.dev.identity.service.impl;

import com.dev.identity.dto.request.UserCreationRequest;
import com.dev.identity.dto.request.UserUpdateRequest;
import com.dev.identity.dto.response.UserResponse;
import com.dev.identity.entity.User;
import com.dev.identity.enums.Role;
import com.dev.identity.exception.AppException;
import com.dev.identity.exception.ErrorCode;
import com.dev.identity.mapper.UserMapper;
import com.dev.identity.repository.UserRepository;
import com.dev.identity.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserServiceImpl implements UserService {

    UserRepository userRepository;

    UserMapper userMapper;

    PasswordEncoder passwordEncoder;

    @Override
    public UserResponse createUser(UserCreationRequest request) {
        if (userRepository.existsByUsername(request.getUsername()))
            throw new AppException(ErrorCode.USER_EXISTED);

        User user = userMapper.toUser(request);

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        HashSet<String> roles = new HashSet<>();
        roles.add(Role.USER.name());
        user.setRoles(roles);

        return userMapper.toUserResponse(userRepository.save(user));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public List<UserResponse> getUsers() {
        getAuthentication();

        return userRepository.findAll()
                .stream()
                .map(userMapper::toUserResponse)
                .toList();
    }

    private static void getAuthentication() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String name = authentication.getName();
        log.info("Username: {}", name);

        authentication.getAuthorities()
                .forEach(grantedAuthority -> log.info(grantedAuthority.getAuthority()));
    }

    @PostAuthorize("returnObject.username == authentication.name")
    @Override
    public UserResponse getUserById(String id) {
        getAuthentication();

        return userRepository.findById(id)
                .map(userMapper::toUserResponse)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
    }

    @Override
    public UserResponse getMyInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String username = authentication.getName();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        return userMapper.toUserResponse(user);
    }

    @Override
    public UserResponse updateUser(String id, UserUpdateRequest request) {
        User user = findById(id);

        userMapper.updateUser(user, request);

        return userMapper.toUserResponse(userRepository.save(user));
    }


    @Override
    public void deleteUserById(String id) {
        findById(id);

        userRepository.deleteById(id);
    }

    private User findById(String id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
    }
}
