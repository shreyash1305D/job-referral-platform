package com.JobReferral.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import com.JobReferral.entities.User;
import com.JobReferral.security.JwtUtil;
import com.JobReferral.service.UserService;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;
    
    @Autowired
    private JwtUtil jwtUtil;

    // ✅ Signup - return full user
    @PostMapping("/signup")
    public ResponseEntity<User> signup(@RequestBody User user) {
        User saved = userService.signup(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    // ✅ Login - return token + user details
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        return userService.login(user.getEmail(), user.getPassword())
                .map(u -> {
                    String token = jwtUtil.generateToken(u.getEmail());
                    Map<String, Object> response = new HashMap<>();
                    response.put("token", "Bearer " + token);
                    response.put("user", u); // full user object
                    return ResponseEntity.ok(response);
                })
                .orElseGet(() -> ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                        Map.of("error", "Invalid credentials!")
                ));
    }


    // ✅ Get user by ID
    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable int id) {
        Optional<User> user = userService.getUser(id);
        return user.map(ResponseEntity::ok)
                   .orElse(ResponseEntity.notFound().build());
    }
}
