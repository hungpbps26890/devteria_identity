package com.dev.identity.service.impl;

import com.dev.identity.dto.request.UserCreationRequest;
import com.dev.identity.dto.response.UserResponse;
import com.dev.identity.entity.User;
import com.dev.identity.exception.AppException;
import com.dev.identity.exception.ErrorCode;
import com.dev.identity.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class UserServiceImplTest {

    @Autowired
    private UserServiceImpl userService;

    @MockitoBean
    private UserRepository userRepository;

    private UserCreationRequest request;

    private UserResponse response;

    private User user;

    @BeforeEach
    public void initData() {
        LocalDate dob = LocalDate.of(2000, 1, 1);

        request = UserCreationRequest.builder()
                .username("john")
                .firstName("John")
                .lastName("Doe")
                .password("12345678")
                .dob(dob)
                .build();

        response = UserResponse.builder()
                .id("abc-123")
                .username("john")
                .firstName("John")
                .lastName("Doe")
                .dob(dob)
                .build();

        user = User.builder()
                .id("abc-123")
                .username("john")
                .firstName("John")
                .lastName("Doe")
                .dob(dob)
                .build();
    }

    @Test
    public void createUser_validRequest_success() {
        // Given
        Mockito.when(userRepository.existsByUsername(Mockito.anyString())).thenReturn(false);

        Mockito.when(userRepository.save(Mockito.any())).thenReturn(user);

        // When
        UserResponse userResponse = userService.createUser(request);

        // Then
        assertThat(userResponse.getId()).isEqualTo(response.getId());
        assertThat(userResponse.getUsername()).isEqualTo(response.getUsername());
        assertThat(userResponse.getFirstName()).isEqualTo(response.getFirstName());
        assertThat(userResponse.getLastName()).isEqualTo(response.getLastName());
        assertThat(userResponse.getDob()).isEqualTo(response.getDob());
    }

    @Test
    public void createUser_userExisted_fail() {
        // Given
        Mockito.when(userRepository.existsByUsername(Mockito.anyString())).thenReturn(true);

        // When
        AppException exception = assertThrows(AppException.class, () -> userService.createUser(request));

        // Then
        assertThat(exception.getErrorCode().getCode()).isEqualTo(ErrorCode.USER_EXISTED.getCode());
        assertThat(exception.getErrorCode().getMessage()).isEqualTo(ErrorCode.USER_EXISTED.getMessage());
    }
}
