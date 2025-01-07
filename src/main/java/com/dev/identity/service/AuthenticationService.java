package com.dev.identity.service;

import com.dev.identity.dto.request.AuthenticationRequest;
import com.dev.identity.dto.request.IntrospectRequest;
import com.dev.identity.dto.request.LogoutRequest;
import com.dev.identity.dto.response.AuthenticationResponse;
import com.dev.identity.dto.response.IntrospectResponse;
import com.nimbusds.jose.JOSEException;

import java.text.ParseException;

public interface AuthenticationService {

    AuthenticationResponse authenticate(AuthenticationRequest request);

    IntrospectResponse introspect(IntrospectRequest request) throws JOSEException, ParseException;

    void logout(LogoutRequest request) throws ParseException, JOSEException;
}
