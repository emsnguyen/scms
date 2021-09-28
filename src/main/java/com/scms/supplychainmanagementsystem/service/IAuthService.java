package com.scms.supplychainmanagementsystem.service;

import com.scms.supplychainmanagementsystem.dto.AuthenticationResponse;
import com.scms.supplychainmanagementsystem.dto.LoginRequest;

public interface IAuthService {

     AuthenticationResponse login(LoginRequest loginRequest);

}
