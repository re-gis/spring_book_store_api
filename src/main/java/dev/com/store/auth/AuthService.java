package dev.com.store.auth;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import dev.com.store.Entities.User;
import dev.com.store.Enums.URole;
import dev.com.store.Repository.UserRepository;
import dev.com.store.Services.JwtService;
import dev.com.store.dtos.LoginRequest;
import dev.com.store.dtos.RegisterRequest;
import dev.com.store.payload.ApiResponse;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public ApiResponse register(@NotNull RegisterRequest registerRequest) {
        var user = User.builder()
                .name(registerRequest.getName())
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .role(URole.ROLE_USER)
                .build();

        userRepository.save(user);
        var token = jwtService.generateToken(user);
        return ApiResponse.builder()
                .data(user)
                .token(token)
                .success(true)
                .message("User registered successfully!")
                .build();
    }

    public ApiResponse authenticate(@NotNull LoginRequest loginRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        var user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User %s not found!", null, null)));

        var token = jwtService.generateToken(user);
        return ApiResponse.builder()
                .token(token)
                .success(true)
                .message("User logged in successfully!")
                .data(user)
                .build();

    }
}
