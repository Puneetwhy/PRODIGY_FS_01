package com.example.SecureUserAuthentication.controller;

import com.example.SecureUserAuthentication.dto.UserDto;
import com.example.SecureUserAuthentication.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    // Register a new user
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserDto userDto) {
        userService.registerUser(userDto); // Register the user
        return ResponseEntity.ok("User registered successfully");
    }

    // Login user
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserDto userDto) {
        // Pass correct parameters: username and password
        boolean isAuthenticated = userService.authenticate(userDto.getUsername(), userDto.getPassword());
        if (isAuthenticated) {
            return ResponseEntity.ok("Login successful");
        } else {
            return ResponseEntity.status(401).body("Invalid username or password");
        }
    }

}
