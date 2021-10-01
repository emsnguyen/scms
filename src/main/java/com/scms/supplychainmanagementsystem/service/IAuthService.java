package com.scms.supplychainmanagementsystem.service;

import com.scms.supplychainmanagementsystem.dto.AuthenticationResponse;
import com.scms.supplychainmanagementsystem.dto.LoginRequest;
import com.scms.supplychainmanagementsystem.dto.RefreshTokenRequest;
import com.scms.supplychainmanagementsystem.dto.RegisterRequest;
import com.scms.supplychainmanagementsystem.entity.User;
import com.scms.supplychainmanagementsystem.entity.VerificationToken;

public interface IAuthService {

    void signup(RegisterRequest registerRequest);

    User getCurrentUser();

    void fetchUserAndEnable(VerificationToken verificationToken);

    String generateVerificationToken(User user);

    void verifyAccount(String token);

    AuthenticationResponse login(LoginRequest loginRequest);

    AuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest);

    boolean isLoggedIn();

}
