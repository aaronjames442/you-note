package com.notes_app.controller;

import com.notes_app.controller.dto.LoginRequest;
import com.notes_app.controller.dto.RegisterUserDto;
import com.notes_app.controller.dto.TokenResponseDto;
import com.notes_app.dao.UserDao;
import com.notes_app.exception.DaoException;
import com.notes_app.model.User;
import com.notes_app.security.jwt.TokenProvider;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@CrossOrigin
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authManager;
    private final TokenProvider tokenProvider;
    private  UserDao userDao;
    private final PasswordEncoder passwordEncoder;

    public AuthController(AuthenticationManager authManager, TokenProvider tokenProvider, UserDao userDao, PasswordEncoder passwordEncoder) {
        this.authManager = authManager;
        this.tokenProvider = tokenProvider;
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public TokenResponseDto login(@RequestBody LoginRequest request) {
        Authentication auth = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        String token = tokenProvider.createToken(auth, Boolean.TRUE.equals(request.getRememberMe()));

        User user = userDao.getUserByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setPassword(null); // don’t leak password

        return new TokenResponseDto(token, user);
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@Valid @RequestBody RegisterUserDto newUser) {
        // 1. Check passwords match
        if (!newUser.isPasswordsMatch()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password and confirm password do not match");
        }

        try {
            // 2. Create a new User object manually
            User user = new User();
            user.setUsername(newUser.getUsername());
            user.setPassword(passwordEncoder.encode(newUser.getPassword())); // hash the password
            user.setRole("ROLE_USER"); // default role

            // 3. Save using DAO
            User created = userDao.createUser(user);

            // 4. Don’t leak password in response
            created.setPassword(null);

            return ResponseEntity.status(HttpStatus.CREATED).body(created);

        } catch (DaoException e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "DAO error - " + e.getMessage()
            );
        }
    }


}
