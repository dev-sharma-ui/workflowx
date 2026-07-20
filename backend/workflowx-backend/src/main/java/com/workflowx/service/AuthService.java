package com.workflowx.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.workflowx.dto.auth.AuthResponse;
import com.workflowx.dto.auth.LoginRequest;
import com.workflowx.dto.auth.RegisterRequest;
import com.workflowx.entity.User;
import com.workflowx.enums.Role;
import com.workflowx.enums.UserStatus;
import com.workflowx.exception.ConflictException;
import com.workflowx.exception.UnauthorizedException;
import com.workflowx.repository.UserRepository;
import com.workflowx.security.JwtService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthResponse register(RegisterRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new ConflictException("Email already registered");
        }

        User user = new User();

        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());

        user.setPassword(
                passwordEncoder.encode(
                        request.getPassword()
                )
        );

        user.setRole(Role.EMPLOYEE);
        user.setStatus(UserStatus.ACTIVE);

        userRepository.save(user);

        String token =
                jwtService.generateToken(
                        user.getEmail(),
                        user.getRole().name()
                );

        return new AuthResponse(token);
    }

    public AuthResponse login(LoginRequest request) {

        User user =
                userRepository.findByEmail(request.getEmail())
                        .orElseThrow(
                                () -> new UnauthorizedException(
                                        "Invalid credentials"
                                )
                        );

        boolean matches =
                passwordEncoder.matches(
                        request.getPassword(),
                        user.getPassword()
                );

        if (!matches) {
            throw new UnauthorizedException(
                    "Invalid credentials"
            );
        }

        String token =
                jwtService.generateToken(
                        user.getEmail(),
                        user.getRole().name()
                );

        return new AuthResponse(token);
    }
}