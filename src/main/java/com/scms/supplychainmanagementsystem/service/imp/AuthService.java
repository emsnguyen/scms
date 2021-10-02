package com.scms.supplychainmanagementsystem.service.imp;

import com.scms.supplychainmanagementsystem.dto.AuthenticationResponse;
import com.scms.supplychainmanagementsystem.dto.LoginRequest;
import com.scms.supplychainmanagementsystem.dto.RefreshTokenRequest;
import com.scms.supplychainmanagementsystem.dto.RegisterRequest;
import com.scms.supplychainmanagementsystem.entity.User;
import com.scms.supplychainmanagementsystem.entity.VerificationToken;
import com.scms.supplychainmanagementsystem.exceptions.AppException;
import com.scms.supplychainmanagementsystem.repository.RoleRepository;
import com.scms.supplychainmanagementsystem.repository.UserRepository;
import com.scms.supplychainmanagementsystem.repository.VerificationTokenRepository;
import com.scms.supplychainmanagementsystem.security.JwtProvider;
import com.scms.supplychainmanagementsystem.service.IAuthService;
import com.scms.supplychainmanagementsystem.service.IRefreshTokenService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class AuthService implements IAuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final VerificationTokenRepository verificationTokenRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final IRefreshTokenService iRefreshTokenService;

    public void signup(RegisterRequest registerRequest) {
        log.info("[Start AuthService - signup for user: " + registerRequest.getUsername() + "]");
        if (userRepository.existsByUsername(registerRequest.getUsername())) {
            throw new AppException("Username exists");
        }
        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setCreatedDate(Instant.now());
        user.setActive(true);
        user.setRole(roleRepository.findByRoleName(registerRequest.getRoleName())
                .orElseThrow(() -> new AppException("Not found role")));
        log.info("[Start save user " + user.getUsername() + "to database]");
        userRepository.save(user);
        log.info("[End save user " + user.getUsername() + "to database]");
        String token = generateVerificationToken(user);
        log.info("[End AuthService - signup for user: " + registerRequest.getUsername() + "]");
    }

    @Transactional(readOnly = true)
    public User getCurrentUser() {
        org.springframework.security.core.userdetails.User principal
                = (org.springframework.security.core.userdetails.User) SecurityContextHolder
                .getContext().getAuthentication().getPrincipal();
        return userRepository.findByUsername(principal.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User name not found - " + principal.getUsername()));
    }

    public void fetchUserAndEnable(VerificationToken verificationToken) {
        String username = verificationToken.getUser().getUsername();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException("User not found with name - " + username));
        user.setActive(true);
        userRepository.save(user);
    }

    public String generateVerificationToken(User user) {
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setUser(user);

        verificationTokenRepository.save(verificationToken);
        return token;
    }

    public void verifyAccount(String token) {
        Optional<VerificationToken> verificationToken = verificationTokenRepository.findByToken(token);
        fetchUserAndEnable(verificationToken.orElseThrow(() -> new AppException("Invalid Token")));
    }

    public AuthenticationResponse login(LoginRequest loginRequest) {
        log.info("[Start AuthService - login with username: " + loginRequest.getUsername() + "]");
        log.info("[Start authenticate user's login information]");
        Authentication authenticate = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername()
                        , loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        log.info("[End authenticate user's login information]");
        log.info("[Generate token for user]");
        String token = jwtProvider.generateToken(authenticate);
        log.info("[Generate token for user login successfully]");
        AuthenticationResponse authenticationResponse = AuthenticationResponse.builder()
                .authenticationToken(token)
                .refreshToken(iRefreshTokenService.generateRefreshToken().getToken())
                .expiresAt(Instant.now().plusMillis(jwtProvider.getJwtExpirationInMillis()))
                .username(loginRequest.getUsername())
                .build();
        log.info("[End AuthService - login with username: " + loginRequest.getUsername() + "]");
        return authenticationResponse;
    }

    public AuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
        iRefreshTokenService.validateRefreshToken(refreshTokenRequest.getRefreshToken());
        String token = jwtProvider.generateTokenWithUserName(refreshTokenRequest.getUsername());
        return AuthenticationResponse.builder()
                .authenticationToken(token)
                .refreshToken(refreshTokenRequest.getRefreshToken())
                .expiresAt(Instant.now().plusMillis(jwtProvider.getJwtExpirationInMillis()))
                .username(refreshTokenRequest.getUsername())
                .build();
    }

    public boolean isLoggedIn() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return !(authentication instanceof AnonymousAuthenticationToken) && authentication.isAuthenticated();
    }

}
