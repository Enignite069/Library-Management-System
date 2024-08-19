package com.library_management.controller.identity;

import java.text.ParseException;

import com.library_management.dto.request.identity.AuthenticationRequest;
import com.library_management.dto.request.identity.IntrospectRequest;
import com.library_management.dto.request.identity.LogOutRequest;
import com.library_management.dto.request.identity.RefreshRequest;
import com.library_management.dto.response.global.ApiResponse;
import com.library_management.dto.response.identity.AuthenticationResponse;
import com.library_management.dto.response.identity.IntrospectResponse;
import com.library_management.service.identity.AuthenticationService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nimbusds.jose.JOSEException;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "Authentication")
@Slf4j
public class AuthenticationController {
    AuthenticationService authenticationService;

    @Operation(
            summary = "Log in and take bearer token for system",
            description = "Input correct available username and password"
    )
    @PostMapping("/log-in")
    ApiResponse<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        var result = authenticationService.authenticate(request);
        log.info("Controller: Authenticating..........");
        return ApiResponse.<AuthenticationResponse>builder().result(result).build();
    }

    @Operation(
            summary = "Verify the Token if it is available or not",
            description = "Input correct token"
    )
    @PostMapping("/introspect")
    ApiResponse<IntrospectResponse> introspect(@RequestBody IntrospectRequest request)
            throws JOSEException, ParseException {
        var result = authenticationService.introspect(request);
        log.info("Controller: Introspecting..........");
        return ApiResponse.<IntrospectResponse>builder().result(result).build();
    }

    @Operation(
            summary = "Log out the token",
            description = "Make the token unavailable from the system"
    )
    @PostMapping("/log-out")
    ApiResponse<Void> logOut(@RequestBody LogOutRequest request) throws JOSEException, ParseException {
        authenticationService.logOut(request);
        log.info("Controller: Logging out..........");
        return ApiResponse.<Void>builder().build();
    }

    @Operation(
            summary = "Refresh Token",
            description = "Extend the duration for an available token"
    )
    @PostMapping("/refresh")
    ApiResponse<AuthenticationResponse> refresh(@RequestBody RefreshRequest request)
            throws ParseException, JOSEException {
        var result = authenticationService.refreshToken(request);
        log.info("Controller: Refreshing..........");
        return ApiResponse.<AuthenticationResponse>builder().result(result).build();
    }
}

