package com.bach.notes.controllers;


import com.bach.notes.dtos.requests.authentications.AuthRequest;
import com.bach.notes.dtos.requests.authentications.IntrospectRequest;
import com.bach.notes.dtos.requests.invalidate_tokens.LogoutRequest;
import com.bach.notes.dtos.responses.ApiResponse;
import com.bach.notes.dtos.responses.authentications.AuthResponse;
import com.bach.notes.dtos.responses.authentications.IntrospectResponse;
import com.bach.notes.services.impl.AuthService;
import com.nimbusds.jose.JOSEException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    AuthService authService;

    @PostMapping("/token")
    public ApiResponse<AuthResponse> login(@RequestBody AuthRequest authRequest) {

        return ApiResponse.<AuthResponse>builder()
                .code(1000)
                .result(authService.authenticate(authRequest))
                .build();
    }

    @PostMapping("/introspect")
    public ApiResponse<IntrospectResponse> introspect(@RequestBody IntrospectRequest authRequest)
            throws ParseException, JOSEException {

        return ApiResponse.<IntrospectResponse>builder()
                .code(1000)
                .result(authService.introspect(authRequest))
                .build();

    }

    @PostMapping("/logout")
    public ApiResponse<Void> logout(LogoutRequest authRequest) throws ParseException, JOSEException {

        authService.logout(authRequest);
        return ApiResponse.<Void>builder()
                .code(1000)
                .message("Logout successfully")
                .build();

    }

}
