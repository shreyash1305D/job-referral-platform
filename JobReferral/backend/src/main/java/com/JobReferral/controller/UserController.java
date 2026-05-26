package com.JobReferral.controller;

import com.JobReferral.entities.User;
import com.JobReferral.security.JwtUtil;
import com.JobReferral.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
@CrossOrigin("*")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody User user) {

        return ResponseEntity.ok(
                userService.signup(user)
        );
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {

        return userService
                .login(user.getEmail(), user.getPassword())
                .map(u -> {

                    String token =
                            jwtUtil.generateToken(u.getEmail());

                    Map<String, Object> response =
                            new HashMap<>();

                    response.put("token", token);
                    response.put("user", u);

                    return ResponseEntity.ok(response);

                }).orElse(ResponseEntity.badRequest()
                        .body("Invalid Credentials"));
    }
}