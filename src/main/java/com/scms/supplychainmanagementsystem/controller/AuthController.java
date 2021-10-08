package com.scms.supplychainmanagementsystem.controller;

import com.scms.supplychainmanagementsystem.dto.AuthenticationResponse;
import com.scms.supplychainmanagementsystem.dto.LoginRequest;
import com.scms.supplychainmanagementsystem.dto.RefreshTokenRequest;
import com.scms.supplychainmanagementsystem.dto.RegisterRequest;
import com.scms.supplychainmanagementsystem.service.IAuthService;
import com.scms.supplychainmanagementsystem.service.IRefreshTokenService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.OK;

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
    public ResponseEntity<String> verifyAccount(@PathVariable String token) {
        iAuthService.verifyAccount(token);
        return new ResponseEntity<>("Account Activated Successfully", OK);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody LoginRequest loginRequest) {
        log.info("[Start AuthController - login with username: " + loginRequest.getUsername() + "]");
        AuthenticationResponse authenticationResponse = iAuthService.login(loginRequest);
        log.info("[End AuthController - login with username: " + loginRequest.getUsername() + "]");
        return ResponseEntity.status(OK).body(authenticationResponse);
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
}
