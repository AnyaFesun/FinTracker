package org.example.back_end_labs.controller;

import jakarta.validation.Valid;
import org.example.back_end_labs.component.JwtUtils;
import org.example.back_end_labs.model.User;
import org.example.back_end_labs.service.AccountService;
import org.example.back_end_labs.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/")
public class UserController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final AccountService accountService;

    @Autowired
    public UserController(UserService userService, PasswordEncoder passwordEncoder, JwtUtils jwtUtils, AccountService accountService) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
        this.accountService = accountService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestParam String username, @RequestParam String password) {
        userService.addUser(username, passwordEncoder.encode(password));

        accountService.createAccount(userService.findByName(username).getId(), 0.0);
        return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully.");
    }

    @PostMapping("/login")
    public ResponseEntity<Object> loginUser(@RequestParam String username, @RequestParam String password) {
        User user = userService.findByName(username);
        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            String token = jwtUtils.generateJwtToken(user.getId());

            Map<String, Object> response = new HashMap<>();
            response.put("token", token);
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password.");
        }
    }
}
