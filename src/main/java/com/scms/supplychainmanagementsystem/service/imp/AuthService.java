package com.scms.supplychainmanagementsystem.service.imp;

import com.scms.supplychainmanagementsystem.dto.AuthenticationResponse;
import com.scms.supplychainmanagementsystem.dto.LoginRequest;
import com.scms.supplychainmanagementsystem.repository.UserRepository;
import com.scms.supplychainmanagementsystem.service.IAuthService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Transactional
public class AuthService implements IAuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    // TODO: add login authentication token
    @Override
    public AuthenticationResponse login(LoginRequest loginRequest) {
        return new AuthenticationResponse();
    }
}
