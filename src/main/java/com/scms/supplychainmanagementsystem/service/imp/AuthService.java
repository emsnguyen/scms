package com.scms.supplychainmanagementsystem.service.imp;

import com.scms.supplychainmanagementsystem.dto.*;
import com.scms.supplychainmanagementsystem.entity.User;
import com.scms.supplychainmanagementsystem.entity.VerificationToken;
import com.scms.supplychainmanagementsystem.entity.Warehouse;
import com.scms.supplychainmanagementsystem.exceptions.AppException;
import com.scms.supplychainmanagementsystem.repository.*;
import com.scms.supplychainmanagementsystem.security.JwtProvider;
import com.scms.supplychainmanagementsystem.service.IAuthService;
import com.scms.supplychainmanagementsystem.service.IRefreshTokenService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    private final MailService mailService;
    private final WarehouseRepository warehouseRepository;
    private final DistrictRepository districtRepository;

    @Override
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
                .orElseThrow(() -> new AppException("Role not found")));
        Warehouse warehouse = new Warehouse();
        if (registerRequest.getRoleName().equals("ADMIN")) {
            warehouse.setWarehouseID(0L);
        } else {
            warehouseRepository.findById(1L).orElseThrow(() -> new AppException("Warehouse 1 not found"));
            warehouse.setWarehouseID(1L);
        }
        user.setWarehouse(warehouse);
        user.setDistrict(districtRepository.findById(1L)
                .orElseThrow(() -> new AppException("District 1 not found")));
        log.info("[Start save user " + user.getUsername() + " to database]");
        userRepository.save(user);
        log.info("[End save user " + user.getUsername() + " to database]");
        log.info("[End AuthService - signup for user: " + registerRequest.getUsername() + "]");
    }

    @Override
    public void verifyAccount(String token) {
        Optional<VerificationToken> verificationToken = verificationTokenRepository.findByToken(token);
        fetchUserAndResetPassword(verificationToken.orElseThrow(() -> new AppException("Invalid Token")));
    }

    @Override
    public AuthenticationResponse login(LoginRequest loginRequest) {
        log.info("[Start AuthService - login with username: " + loginRequest.getUsername() + "]");
        log.info("[Start authenticate user's login information]");
        Authentication authenticate = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername()
                        , loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        log.info("[Authenticate user's login successful]");
        String token = jwtProvider.generateToken(authenticate);
        log.info("[Generate token for user login with username " + loginRequest.getUsername() + " successfully]");
        AuthenticationResponse authenticationResponse = AuthenticationResponse.builder()
                .authenticationToken(token)
                .refreshToken(iRefreshTokenService.generateRefreshToken().getToken())
                .expiresAt(Instant.now().plusMillis(jwtProvider.getJwtExpirationInMillis()))
                .username(loginRequest.getUsername())
                .build();
        log.info("[End AuthService - login with username: " + loginRequest.getUsername() + "]");
        return authenticationResponse;
    }

    @Override
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

    @Override
    public void forgotPassword(ForgotPasswordRequest forgotPasswordRequest) {
        log.info("[Start AuthService - Forgot Password Username " + forgotPasswordRequest.getUsername() + "]");
        User user = userRepository.findByUsername(forgotPasswordRequest.getUsername())
                .orElseThrow(() -> new AppException("User not found"));
        if (user.getEmail().equals(forgotPasswordRequest.getEmail())) {
            String token = generateVerificationToken(user);
            mailService.sendMail(new NotificationEmail("[Request password change]Please verify your account",
                    user.getEmail(), "If you did not make this request then please ignore this email." +
                    "Please click on the below url to verify your account : " +
                    "http://localhost:8080/api/auth/accountVerification/" + token + ""));
        } else {
            throw new AppException("Email not match with username");
        }
        log.info("[End AuthService - Forgot Password Username " + forgotPasswordRequest.getUsername() + "]");
    }

    public String generateVerificationToken(User user) {
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setUser(user);
        verificationTokenRepository.save(verificationToken);
        return token;
    }

    private void fetchUserAndResetPassword(VerificationToken verificationToken) {
        String username = verificationToken.getUser().getUsername();
        User user = userRepository.findByUsername(username).orElseThrow(() -> new AppException("User not found"));
        user.setPassword(passwordEncoder.encode("123@456"));
        userRepository.save(user);
    }

}
