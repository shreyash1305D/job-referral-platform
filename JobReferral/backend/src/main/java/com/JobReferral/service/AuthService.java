package com.JobReferral.service;

import com.JobReferral.entities.User;
import com.JobReferral.repository.UserRepository;
import com.JobReferral.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class AuthService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private JwtUtil jwtUtil;
    
    public Map<String, Object> register(String email, String password, String firstName, 
                                        String lastName, String role, String company) {
        Map<String, Object> response = new HashMap<>();
        
        if (userRepository.existsByEmail(email)) {
            response.put("success", false);
            response.put("message", "Email already exists");
            return response;
        }
        
        User user = new User();
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setRole(role);
        user.setCompany(company);
        user.setIsActive(true);
        
        User savedUser = userRepository.save(user);
        
        String token = jwtUtil.generateToken(email, savedUser.getId());
        
        response.put("success", true);
        response.put("message", "Registration successful");
        response.put("token", token);
        response.put("user", savedUser);
        
        return response;
    }
    
    public Map<String, Object> login(String email, String password) {
        Map<String, Object> response = new HashMap<>();
        
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isEmpty()) {
            response.put("success", false);
            response.put("message", "User not found");
            return response;
        }
        
        if (!passwordEncoder.matches(password, user.get().getPassword())) {
            response.put("success", false);
            response.put("message", "Invalid password");
            return response;
        }
        
        String token = jwtUtil.generateToken(email, user.get().getId());
        
        response.put("success", true);
        response.put("message", "Login successful");
        response.put("token", token);
        response.put("user", user.get());
        
        return response;
    }
    
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }
    
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}