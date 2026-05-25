package com.JobReferral.controller;

import com.JobReferral.service.AdminService;
import com.JobReferral.entities.Referral.Status;
import com.JobReferral.entities.User;
import com.JobReferral.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private UserRepository userRepository;

    // ✅ Recruiter details by ID
    @GetMapping("/users/{id}")
    public User getRecruiterDetails(@PathVariable int id) {
        return userRepository.findById(id).orElse(null);
    }

    // ✅ Recruiter jobs count
    @GetMapping("/jobs/count")
    public Map<String, Long> getRecruiterJobCount(@RequestParam(required = false) Integer recruiterId) {
        Map<String, Long> result = new HashMap<>();
        if (recruiterId != null) {
            result.put("count", adminService.getRecruiterJobCount(recruiterId));
        } else {
            result.put("count", adminService.getJobCount());
        }
        return result;
    }

    // ✅ Recruiter referrals breakdown
    @GetMapping("/recruiter/{id}/referrals")
    public Map<String, Long> getRecruiterReferrals(@PathVariable int id) {
        Map<String, Long> result = new HashMap<>();
        result.put("Accepted", adminService.getRecruiterReferralCount(id, Status.accepted));
        result.put("Rejected", adminService.getRecruiterReferralCount(id, Status.rejected));
        result.put("Pending", adminService.getRecruiterReferralCount(id, Status.requested));
        return result;
    }
}
