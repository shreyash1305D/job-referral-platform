package com.JobReferral.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.JobReferral.entities.User;
import com.JobReferral.repository.UserRepository;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User signup(User user) {
        return userRepository.save(user);
    }

    public Optional<User> login(String email, String password) {
        Optional<User> existingUser = userRepository.findByEmail(email);
        if (existingUser.isPresent() && existingUser.get().getPassword().equals(password)) {
            return existingUser;
        }
        return Optional.empty();
    }

    public Optional<User> getUser(int id) {
        return userRepository.findById(id);
    }
}
