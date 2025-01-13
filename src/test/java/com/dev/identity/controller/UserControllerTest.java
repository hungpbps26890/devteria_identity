package com.dev.identity.controller;

import com.dev.identity.dto.request.UserCreationRequest;
import com.dev.identity.dto.response.UserResponse;
import com.dev.identity.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    private UserCreationRequest request;

    private UserResponse response;

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
    }

    @Test
    public void createUser_validRequest_success() throws Exception {
        // Given
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        String content = objectMapper.writeValueAsString(request);

        Mockito.when(userService.createUser(ArgumentMatchers.any())).thenReturn(response);

        // When and Then
        mockMvc
                // When
                .perform(MockMvcRequestBuilders
                        .post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content)
                )
                // Then
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value(201))
                .andExpect(MockMvcResultMatchers.jsonPath("data.id").value(response.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("data.username").value(response.getUsername()))
                .andExpect(MockMvcResultMatchers.jsonPath("data.firstName").value(response.getFirstName()))
                .andExpect(MockMvcResultMatchers.jsonPath("data.lastName").value(response.getLastName()))
                .andExpect(MockMvcResultMatchers.jsonPath("data.dob").value(response.getDob().toString()));
    }

    @Test
    public void createUser_invalidUsername_fail() throws Exception {
        // Given
        request.setUsername("jo");

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        String content = objectMapper.writeValueAsString(request);

        // When and Then
        mockMvc
                // When
                .perform(MockMvcRequestBuilders
                        .post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content)
                )
                // Then
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value(400))
                .andExpect(MockMvcResultMatchers.jsonPath("message").value("Username must be at least 3 characters"));
    }
}
