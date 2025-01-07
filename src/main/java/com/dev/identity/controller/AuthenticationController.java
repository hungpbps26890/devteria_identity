package com.dev.identity.controller;

import com.dev.identity.dto.request.AuthenticationRequest;
import com.dev.identity.dto.request.IntrospectRequest;
import com.dev.identity.dto.request.LogoutRequest;
import com.dev.identity.dto.response.ApiResponse;
import com.dev.identity.dto.response.AuthenticationResponse;
import com.dev.identity.dto.response.IntrospectResponse;
import com.dev.identity.service.AuthenticationService;
import com.nimbusds.jose.JOSEException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationController {

    AuthenticationService authenticationService;

    @PostMapping("/login")
    public ApiResponse<AuthenticationResponse> login(@RequestBody AuthenticationRequest request) {
        AuthenticationResponse response = authenticationService.authenticate(request);

        return ApiResponse.<AuthenticationResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Login successfully")
                .data(response)
                .build();
    }

    @PostMapping("/introspect")
    public ApiResponse<IntrospectResponse> introspect(@RequestBody IntrospectRequest request)
            throws ParseException, JOSEException {
        IntrospectResponse response = authenticationService.introspect(request);

        return ApiResponse.<IntrospectResponse>builder()
                .code(HttpStatus.OK.value())
                .data(response)
                .build();
    }

    @PostMapping("/logout")
    public ApiResponse<Void> logout(@RequestBody LogoutRequest request) throws ParseException, JOSEException {
        authenticationService.logout(request);

        return ApiResponse.<Void>builder()
                .code(HttpStatus.OK.value())
                .message("Logout successfully")
                .build();
    }
}
