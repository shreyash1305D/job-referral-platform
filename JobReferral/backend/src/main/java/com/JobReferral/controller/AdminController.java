package com.JobReferral.controller;

import com.JobReferral.entities.Referral;
import com.JobReferral.entities.Status;
import com.JobReferral.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:3000"})
public class AdminController {
    
    @Autowired
    private AdminService adminService;
    
    @GetMapping("/dashboard")
    public ResponseEntity<?> getDashboard() {
        Map<String, Object> dashboard = new HashMap<>();
        dashboard.put("totalReferrals", adminService.getTotalReferrals());
        dashboard.put("acceptedReferrals", adminService.getAcceptedReferrals());
        dashboard.put("pendingReferrals", adminService.getPendingReferrals());
        dashboard.put("rejectedReferrals", adminService.getRejectedReferrals());
        return ResponseEntity.ok(dashboard);
    }
    
    @GetMapping("/referrals")
    public ResponseEntity<?> getAllReferrals() {
        List<Referral> referrals = adminService.getAllReferrals();
        return ResponseEntity.ok(referrals);
    }
    
    @GetMapping("/referrals/status/{status}")
    public ResponseEntity<?> getReferralsByStatus(@PathVariable String status) {
        try {
            Status statusEnum = Status.valueOf(status.toUpperCase());
            List<Referral> referrals = adminService.getReferralsByStatus(statusEnum);
            return ResponseEntity.ok(referrals);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Invalid status");
        }
    }
}