// src/main/java/com/projects/blog/controllers/AuthController.java
package com.projects.blog.controllers;

import com.projects.blog.payloads.JwtAuthRequest;
import com.projects.blog.payloads.JwtAuthResponse;
import com.projects.blog.payloads.UserDTO;
import com.projects.blog.security.JwtService;
import com.projects.blog.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtService jwtService;
    private final UserService userService;

    /**
     * Handles user login and returns a JWT upon successful authentication.
     * @param request The login request containing username and password.
     * @return A response entity with the JWT.
     */
    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> createToken(@RequestBody JwtAuthRequest request) {
        // 1. Authenticate the user
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        // 2. If authentication is successful, generate a token
        final UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
        final String token = jwtService.generateToken(userDetails);

        // 3. Return the token in the response
        return ResponseEntity.ok(new JwtAuthResponse(token));
    }

    /**
     * Handles new user registration.
     * This moves the user creation logic to a public endpoint.
     * @param userDto The user data for registration.
     * @return The created user's data.
     */
    @PostMapping("/register")
    public ResponseEntity<UserDTO> registerUser(@RequestBody UserDTO userDto) {
        UserDTO registeredUser = this.userService.createUser(userDto);
        return new ResponseEntity<>(registeredUser, HttpStatus.CREATED);
    }
}
