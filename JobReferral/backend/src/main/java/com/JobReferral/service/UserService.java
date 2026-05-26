package com.JobReferral.service;

import com.JobReferral.entities.User;
import com.JobReferral.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private final BCryptPasswordEncoder encoder =
            new BCryptPasswordEncoder();

    public User signup(User user) {

        user.setPassword(
                encoder.encode(user.getPassword())
        );

        return userRepository.save(user);
    }

    public Optional<User> login(String email, String password) {

        Optional<User> existingUser =
                userRepository.findByEmail(email);

        if (existingUser.isPresent() &&
                encoder.matches(password,
                        existingUser.get().getPassword())) {

            return existingUser;
        }

        return Optional.empty();
    }
}