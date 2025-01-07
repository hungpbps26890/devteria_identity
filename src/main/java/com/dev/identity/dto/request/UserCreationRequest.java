package com.dev.identity.dto.request;

import com.dev.identity.validator.DobConstraint;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

    @Size(min = 3, message = "INVALID_USERNAME_SIZE")
    @NotBlank(message = "INVALID_USERNAME_REQUIRED")
    String username;

    @Size(min = 8, message = "INVALID_PASSWORD_SIZE")
    @NotBlank(message = "INVALID_PASSWORD_REQUIRED")
    String password;

    String firstName;

    String lastName;

    @DobConstraint(min = 18, message = "INVALID_DOB_AGE")
    @NotNull(message = "INVALID_DOB_REQUIRED")
    LocalDate dob;
}
