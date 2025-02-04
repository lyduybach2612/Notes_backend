package com.bach.notes.services;

import com.bach.notes.dtos.requests.authentications.AuthRequest;
import com.bach.notes.dtos.requests.authentications.IntrospectRequest;
import com.bach.notes.dtos.requests.invalidate_tokens.LogoutRequest;
import com.bach.notes.dtos.responses.authentications.AuthResponse;
import com.bach.notes.dtos.responses.authentications.IntrospectResponse;
import com.nimbusds.jose.JOSEException;

import java.text.ParseException;

public interface IAuthService {

    AuthResponse authenticate(AuthRequest authRequest);
    IntrospectResponse introspect(IntrospectRequest introspectRequest) throws JOSEException, ParseException;
    void logout(LogoutRequest logoutRequest) throws ParseException, JOSEException;

}
