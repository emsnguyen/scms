package com.scms.supplychainmanagementsystem.service;

import com.scms.supplychainmanagementsystem.entity.RefreshToken;

public interface IRefreshTokenService {
    RefreshToken generateRefreshToken();

    void validateRefreshToken(String token);

    void deleteRefreshToken(String token);

}
