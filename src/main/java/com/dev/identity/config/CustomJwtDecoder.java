package com.dev.identity.config;

import com.dev.identity.dto.request.IntrospectRequest;
import com.dev.identity.dto.response.IntrospectResponse;
import com.dev.identity.exception.ErrorCode;
import com.dev.identity.service.AuthenticationService;
import com.nimbusds.jose.JOSEException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.text.ParseException;
import java.util.Objects;

@Component
public class CustomJwtDecoder implements JwtDecoder {

    @Value("${jwt.signer-key}")
    private String SIGNER_KEY;

    private final AuthenticationService authenticationService;

    private NimbusJwtDecoder nimbusJwtDecoder;

    public CustomJwtDecoder(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @Override
    public Jwt decode(String token) throws JwtException {
        try {
            IntrospectRequest request = IntrospectRequest.builder()
                    .token(token)
                    .build();

            IntrospectResponse response = authenticationService.introspect(request);

            if (!response.isValid()) {
                throw new JwtException(ErrorCode.INVALID_TOKEN.getMessage());
            }
        } catch (ParseException | JOSEException e) {
            throw new JwtException(e.getMessage());
        }

        SecretKeySpec secretKeySpec = new SecretKeySpec(SIGNER_KEY.getBytes(), MacAlgorithm.HS512.getName());

        if (Objects.isNull(nimbusJwtDecoder)) {
            nimbusJwtDecoder = NimbusJwtDecoder
                    .withSecretKey(secretKeySpec)
                    .macAlgorithm(MacAlgorithm.HS512)
                    .build();
        }

        return nimbusJwtDecoder.decode(token);
    }
}
