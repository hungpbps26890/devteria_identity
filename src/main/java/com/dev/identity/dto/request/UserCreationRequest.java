package com.dev.identity.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCreationRequest {

    @Size(min = 3, message = "Username must be at least {min} characters")
    @NotBlank(message = "Username is required")
    String username;

    @Size(min = 8, message = "Password must be at least {min} characters")
    @NotBlank(message = "Password is required")
    String password;

    String firstName;

    String lastName;

    LocalDate dob;
}
