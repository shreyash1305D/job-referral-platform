package com.JobReferral.controller;

import com.JobReferral.entities.Referral;
import com.JobReferral.entities.User;
import com.JobReferral.entities.JobPosting;
import com.JobReferral.entities.Status;
import com.JobReferral.service.AuthService;
import com.JobReferral.service.JobService;
import com.JobReferral.service.ReferralService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/referrals")
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:3000"})
public class ReferralController {
    
    @Autowired
    private ReferralService referralService;
    
    @Autowired
    private AuthService authService;
    
    @Autowired
    private JobService jobService;
    
    @PostMapping
    public ResponseEntity<?> createReferral(@RequestBody Referral referral) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<User> user = authService.getUserByEmail(email);
        
        if (user.isPresent()) {
            referral.setReferrer(user.get());
            Referral created = referralService.createReferral(referral);
            return ResponseEntity.ok(created);
        }
        return ResponseEntity.badRequest().body("User not found");
    }
    
    @GetMapping("/job/{jobId}")
    public ResponseEntity<?> getReferralsByJob(@PathVariable Long jobId) {
        List<Referral> referrals = referralService.getReferralsByJobId(jobId);
        return ResponseEntity.ok(referrals);
    }
    
    @GetMapping("/my-referrals")
    public ResponseEntity<?> getMyReferrals() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<User> user = authService.getUserByEmail(email);
        
        if (user.isPresent()) {
            List<Referral> referrals = referralService.getReferralsByReferrerId(user.get().getId());
            return ResponseEntity.ok(referrals);
        }
        return ResponseEntity.badRequest().body("User not found");
    }
    
    @PutMapping("/{id}/status")
    public ResponseEntity<?> updateReferralStatus(@PathVariable Long id, @RequestBody java.util.Map<String, String> request) {
        String status = request.get("status");
        Referral updated = referralService.updateReferralStatus(id, status);
        if (updated != null) {
            return ResponseEntity.ok(updated);
        }
        return ResponseEntity.badRequest().body("Invalid status or referral not found");
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> getReferralById(@PathVariable Long id) {
        Optional<Referral> referral = referralService.getReferralById(id);
        if (referral.isPresent()) {
            return ResponseEntity.ok(referral.get());
        }
        return ResponseEntity.notFound().build();
    }
}