package com.dev.identity.dto.request;

import com.dev.identity.validator.DobConstraint;
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

    @DobConstraint(min = 18, message = "Age must be greater than or equal to {min}")
    LocalDate dob;
}
