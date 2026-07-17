package com.complaint.service;

import com.complaint.entity.User;
import com.complaint.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User register(String name, String email, String password, String role) {
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password); // NOTE: plain text abhi, baad mein BCryptPasswordEncoder use karna seekhna
        user.setRole(role);
        return userRepository.save(user);
    }

    public boolean emailExists(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    // Login check: email + password match karta hai to user return karo, warna null
    public User login(String email, String password) {
        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isPresent() && userOpt.get().getPassword().equals(password)) {
            return userOpt.get();
        }
        return null;
    }

    public List<User> getStaffList() {
        return userRepository.findByRole("STAFF");
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
