package com.JobReferral.controller;

import com.JobReferral.entities.Referral;
import com.JobReferral.entities.Status;
import com.JobReferral.service.AdminService;
import com.JobReferral.service.ReferralService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/analytics")
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:3000"})
public class ReferralAnalyticsController {
    
    @Autowired
    private AdminService adminService;
    
    @Autowired
    private ReferralService referralService;
    
    @GetMapping("/referral-stats")
    public ResponseEntity<?> getReferralStats() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("total", adminService.getTotalReferrals());
        stats.put("accepted", adminService.getAcceptedReferrals());
        stats.put("pending", adminService.getPendingReferrals());
        stats.put("rejected", adminService.getRejectedReferrals());
        
        double acceptanceRate = adminService.getTotalReferrals() > 0
                ? (double) adminService.getAcceptedReferrals() / adminService.getTotalReferrals() * 100
                : 0;
        stats.put("acceptanceRate", String.format("%.2f", acceptanceRate) + "%");
        
        return ResponseEntity.ok(stats);
    }
}