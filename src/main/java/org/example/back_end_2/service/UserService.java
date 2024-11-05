package org.example.back_end_2.service;

import org.example.back_end_2.model.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final List<User> users = new ArrayList<>();

    public List<User> getAllUsers() {
        return users;
    }

    public Optional<User> getUserById(Long userId) {
        return users.stream().filter(user -> user.getId().equals(userId)).findFirst();
    }

    public void addUser(User user) {
        users.add(user);
    }

    public void deleteUser(Long userId) {
        users.removeIf(user -> user.getId().equals(userId));
    }

}
