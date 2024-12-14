package org.example.back_end_labs.service;

import org.example.back_end_labs.model.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class UserService {
    private final Map<Long, User> users = new ConcurrentHashMap<>();
    private final AtomicLong userIdCounter = new AtomicLong();

    public List<User> getAllUsers() {
        return new ArrayList<>(users.values());
    }

    public User addUser(String name) {
        Long id = userIdCounter.incrementAndGet();
        User user = new User(id, name);
        users.put(id, user);
        return user;
    }

    public User getUserById(Long id) {
        return users.get(id);
    }

    public boolean deleteUser(Long id) {
        return users.remove(id) != null;
    }
}
