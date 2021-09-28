package com.scms.supplychainmanagementsystem.controller;

import com.scms.supplychainmanagementsystem.dto.AuthenticationResponse;
import com.scms.supplychainmanagementsystem.dto.LoginRequest;
import com.scms.supplychainmanagementsystem.service.IAuthService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/auth")
@AllArgsConstructor
@Slf4j
public class AuthController {

    private final IAuthService iAuthService;

    @PostMapping("/login")
    public AuthenticationResponse login(@RequestBody LoginRequest loginRequest) {
        return iAuthService.login(loginRequest);
    }
}
