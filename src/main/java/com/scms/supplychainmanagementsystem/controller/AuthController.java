package com.scms.supplychainmanagementsystem.controller;

import com.scms.supplychainmanagementsystem.dto.*;
import com.scms.supplychainmanagementsystem.service.IAuthService;
import com.scms.supplychainmanagementsystem.service.IRefreshTokenService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
@Slf4j
public class AuthController {

    private final IAuthService iAuthService;
    private final IRefreshTokenService iRefreshTokenService;

    @PostMapping("/signup")
    @ApiOperation(value = "[TEST] Create user")
    public ResponseEntity<String> signup(@RequestBody RegisterRequest registerRequest) {
        log.info("[Start AuthController - signup for user: " + registerRequest.getUsername() + "]");
        iAuthService.signup(registerRequest);
        log.info("[End AuthController - signup for user: " + registerRequest.getUsername() + "]");
        return new ResponseEntity<>("User Registration Successful", OK);
    }

    @GetMapping("accountVerification/{token}")
    public ResponseEntity<Map<String, Object>> verifyAccount(@PathVariable String token) {
        log.info("[Start AuthController - accountVerification]");
        Map<String, Object> result = new HashMap<>();
        iAuthService.verifyAccount(token);
        result.put("message", "Verify account successfully");
        log.info("[Start AuthController - accountVerification]");
        return status(HttpStatus.OK).body(result);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody LoginRequest loginRequest) {
        log.info("[Start AuthController - login with username: " + loginRequest.getUsername() + "]");
        Map<String, Object> result = new HashMap<>();
        AuthenticationResponse authenticationResponse = iAuthService.login(loginRequest);
        result.put("data", authenticationResponse);
        result.put("message", OK);
        log.info("[End AuthController - login with username: " + loginRequest.getUsername() + "]");
        return status(HttpStatus.OK).body(result);
    }

    @PostMapping("/refresh/token")
    public AuthenticationResponse refreshTokens(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
        return iAuthService.refreshToken(refreshTokenRequest);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
        iRefreshTokenService.deleteRefreshToken(refreshTokenRequest.getRefreshToken());
        return ResponseEntity.status(OK).body("Refresh Token Deleted Successfully");
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<Map<String, Object>> forgotPassword(@Valid @RequestBody ForgotPasswordRequest forgotPasswordRequest) {
        log.info("[Start AuthController - Forgot Password Username " + forgotPasswordRequest.getUsername() + "]");
        NotificationEmail noti = iAuthService.forgotPassword(forgotPasswordRequest);
        Map<String, Object> result = new HashMap<>();
        result.put("data", noti);
        result.put("message", OK);
        log.info("[End AuthController - Forgot Password Username " + forgotPasswordRequest.getUsername() + "]");
        return status(HttpStatus.OK).body(result);
    }
}
