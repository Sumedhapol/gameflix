package com.example.gameflix.controller;

import com.example.gameflix.dto.RegisterRequest;
import com.example.gameflix.dto.UserResponse;
import com.example.gameflix.model.User;
import com.example.gameflix.repository.UserRepository;
import com.example.gameflix.config.JwtService;
import com.example.gameflix.dto.LoginRequest;
import com.example.gameflix.dto.LoginResponse;
import jakarta.validation.Valid;
import java.util.Locale;
import jakarta.validation.Valid;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthController(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(
            @Valid @RequestBody RegisterRequest request) {

        String email = request.email().trim()
                .toLowerCase(Locale.ROOT);
        String password = request.password();

        if (password.getBytes(StandardCharsets.UTF_8).length > 72) {
            return ResponseEntity.badRequest().body(
                    Map.of("error",
                            "Password must not exceed 72 UTF-8 bytes"));
        }

        if (userRepository.existsByEmail(email)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(
                    Map.of("error", "Email already registered"));
        }

        User user = new User();
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));

        try {
            User savedUser = userRepository.save(user);

            return ResponseEntity.status(HttpStatus.CREATED).body(
                    new UserResponse(
                            savedUser.getId(),
                            savedUser.getEmail()));
        } catch (DataIntegrityViolationException exception) {
            // Also handles a duplicate email submitted concurrently.
            return ResponseEntity.status(HttpStatus.CONFLICT).body(
                    Map.of("error", "Email already registered"));
        }
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(
            @Valid @RequestBody LoginRequest request) {

        String email = request.email().trim()
                .toLowerCase(Locale.ROOT);

        var user = userRepository.findByEmail(email);

        if (user.isEmpty()
                || !passwordEncoder.matches(
                request.password(),
                user.get().getPassword())) {

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Invalid email or password"));
        }

        String token = jwtService.generateToken(email);

        return ResponseEntity.ok(
                new LoginResponse(
                        token,
                        "Bearer",
                        jwtService.getExpirationMs()));
    }
}