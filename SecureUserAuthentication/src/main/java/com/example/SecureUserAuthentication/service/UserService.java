package com.example.SecureUserAuthentication.service;

import com.example.SecureUserAuthentication.dto.UserDto;
import com.example.SecureUserAuthentication.entity.User;
import com.example.SecureUserAuthentication.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    // Constructor-based dependency injection
    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Method to register a new user
    public User registerUser(UserDto userDto) {
        String encodedPassword = passwordEncoder.encode(userDto.getPassword());

        // Create a new User entity using the data from UserDto
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setPassword(encodedPassword);
        user.setRoles(List.of("USER")); // Assign default roles

        return userRepository.save(user); // Save the user to the database
    }

    // Method to authenticate user (e.g., login)
    public boolean authenticate(String username, String password) {
        User user = userRepository.findByUsername(username).orElse(null);

        if (user != null) {
            // Check if the password matches
            return passwordEncoder.matches(password, user.getPassword());
        }

        return false; // User not found or invalid password
    }
}
