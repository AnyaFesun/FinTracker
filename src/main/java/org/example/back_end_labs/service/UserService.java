package org.example.back_end_labs.service;

import org.example.back_end_labs.model.User;
import org.example.back_end_labs.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void addUser(String username, String password) {
        userRepository.findByName(username)
                .ifPresent(existingAccount -> {
                            throw new IllegalArgumentException("A user with this name already exists, " +
                                    "please choose another name to register.");
                });
        User user = new User();
        user.setName(username);
        user.setPassword(password);
        userRepository.save(user);
    }

    public User findByName(String name) {
        return userRepository.findByName(name).orElse(null);
    }

    public User getUserById(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }
}