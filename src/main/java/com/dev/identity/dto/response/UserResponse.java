package com.dev.identity.dto.response;

import com.dev.identity.entity.Role;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse {

    String id;

    String username;

    String firstName;

    String lastName;

    LocalDate dob;

    Set<Role> roles;
}
