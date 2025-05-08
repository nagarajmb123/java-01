package io.aiven.spring.mysql.demo.controller;

import io.aiven.spring.mysql.demo.dto.LoginDTO;
import io.aiven.spring.mysql.demo.dto.UserDTO;
import io.aiven.spring.mysql.demo.model.User;
import io.aiven.spring.mysql.demo.repository.UserRepository;
import io.aiven.spring.mysql.demo.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserDTO userDTO) {
        // Check for duplicates
        Optional<User> existingUser = userRepository.findByUsername(userDTO.getUsername());
        if (existingUser.isPresent()) {
            return ResponseEntity.badRequest()
                .body(new ErrorResponse("Username already exists"));
        }

        User user = new User(userDTO.getUsername(), userDTO.getEmail(), userDTO.getUsername());
        userRepository.save(user);
        String token = jwtUtil.generateToken(user.getEmail(), user.getUsername());
        
        return ResponseEntity.ok(new RegistrationResponse(token));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) {
        Optional<User> user = userRepository.findByUsername(loginDTO.getUsername());
        if (user.isEmpty() || !user.get().getEmail().equals(loginDTO.getEmail())) {
            return ResponseEntity.badRequest()
                .body(new ErrorResponse("Invalid credentials"));
        }
        String token = jwtUtil.generateToken(loginDTO.getEmail(), loginDTO.getUsername());
        return ResponseEntity.ok(new LoginResponse(token));
    }
}

class ErrorResponse {
    private final String message;

    public ErrorResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}

class RegistrationResponse {
    private final String token;

    public RegistrationResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}

class LoginResponse {
    private final String token;

    public LoginResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}