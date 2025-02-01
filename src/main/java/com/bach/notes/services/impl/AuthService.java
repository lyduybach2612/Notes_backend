package com.bach.notes.services.impl;

import com.bach.notes.dtos.requests.authentications.AuthRequest;
import com.bach.notes.dtos.requests.authentications.IntrospectRequest;
import com.bach.notes.dtos.responses.authentications.AuthResponse;
import com.bach.notes.dtos.responses.authentications.IntrospectResponse;
import com.bach.notes.exceptions.AppException;
import com.bach.notes.exceptions.ErrorCode;
import com.bach.notes.models.User;
import com.bach.notes.repositories.UserRepository;
import com.bach.notes.services.IAuthService;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class AuthService implements IAuthService {

    private static final Logger log = LoggerFactory.getLogger(AuthService.class);
    UserRepository userRepository;
    PasswordEncoder passwordEncoder;
    @NonFinal
    @Value( "${jwt.signerKey}")
    protected String SIGNER_KEY;

    @Override
    public AuthResponse authenticate(AuthRequest authRequest) {

        User user = userRepository.findByUsername(authRequest.getUsername()).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        AuthResponse authResponse = new AuthResponse();
        authResponse.setAuthenticated(passwordEncoder.matches(authRequest.getPassword(), user.getPassword()));
        if(!authResponse.isAuthenticated()){
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
        authResponse.setToken(generateToken(user.getId()));
        return authResponse;

    }

    @Override
    public IntrospectResponse introspect(IntrospectRequest introspectRequest) throws JOSEException, ParseException {

        String token = introspectRequest.getToken();
        JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());
        SignedJWT signedJWT = SignedJWT.parse(token);
        Date expiration = signedJWT.getJWTClaimsSet().getExpirationTime();
        var verified = signedJWT.verify(verifier);
        return IntrospectResponse.builder()
                .valid(verified && expiration.after(new Date()))
                .build();

    }

    private String generateToken(Long userId) {

        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);
        JWTClaimsSet claims = new JWTClaimsSet.Builder()
                .issuer("bachly")
                .issueTime(new Date())
                .expirationTime(new Date(Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli()))
                .claim("userId", userId)
                .build();
        Payload payload = new Payload(claims.toJSONObject());
        JWSObject jwsObject = new JWSObject(header, payload);
        try {
            jwsObject.sign(new MACSigner(SIGNER_KEY));
            return jwsObject.serialize();
        }catch (JOSEException e) {
            log.error("Cannot generate token", e);
            throw new RuntimeException(e.getMessage());
        }

    }

}
