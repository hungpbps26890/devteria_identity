package com.dev.identity.config;

import com.dev.identity.entity.User;
import com.dev.identity.enums.Role;
import com.dev.identity.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;

@Configuration
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Slf4j
public class ApplicationConfig {

    PasswordEncoder passwordEncoder;
    UserRepository userRepository;

    @Bean
    public ApplicationRunner applicationRunner() {
        return args -> {
            if (userRepository.findByUsername("admin").isEmpty()) {
//                HashSet<String> roles = new HashSet<>();
//                roles.add(Role.ADMIN.name());
//                roles.add(Role.USER.name());

                User user = User.builder()
                        .username("admin")
                        .password(passwordEncoder.encode("123456"))
//                        .roles(roles)
                        .firstName("Admin")
                        .lastName("Nguyen")
                        .build();

                userRepository.save(user);

                log.info("Admin created with default username: admin");
            }
        };
    }
}
