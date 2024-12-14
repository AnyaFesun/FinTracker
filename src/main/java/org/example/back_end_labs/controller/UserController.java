package org.example.back_end_labs.controller;

import org.example.back_end_labs.model.User;
import org.example.back_end_labs.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/user")
    public User createUser(@RequestParam String name) {
        return userService.addUser(name);
    }

    @GetMapping("/user/{userId}")
    public Optional<User> getUser(@PathVariable Long userId) {
        return Optional.ofNullable(userService.getUserById(userId));
    }

    @DeleteMapping("/user/{userId}")
    public void deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }
}
