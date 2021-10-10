package com.scms.supplychainmanagementsystem.service;

import com.scms.supplychainmanagementsystem.dto.*;

public interface IAuthService {

    void signup(RegisterRequest registerRequest);

    void forgotPassword(ForgotPasswordRequest forgotPasswordRequest);

    void verifyAccount(String token);

    AuthenticationResponse login(LoginRequest loginRequest);

    AuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest);

}
