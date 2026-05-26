package com.JobReferral.controller;

import com.JobReferral.entities.Referral.Status;
import com.JobReferral.service.ReferralService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/analytics")
public class ReferralAnalyticsController {

    @Autowired
    private ReferralService referralService;

    // ✅ Overall referral status distribution
    @GetMapping("/referrals")
    public Map<String, Long> getReferralAnalytics() {
        Map<String, Long> analytics = new HashMap<>();
        analytics.put("Accepted", referralService.countByStatus(Status.accepted));
        analytics.put("Rejected", referralService.countByStatus(Status.rejected));
        analytics.put("Pending", referralService.countByStatus(Status.requested));
        return analytics;
    }

    // ✅ Recruiter-wise referral counts
    @GetMapping("/recruiter/{id}")
    public Map<String, Long> getRecruiterAnalytics(@PathVariable int id) {
        Map<String, Long> analytics = new HashMap<>();
        analytics.put("Accepted", referralService.countRecruiterReferrals(id, Status.accepted));
        analytics.put("Rejected", referralService.countRecruiterReferrals(id, Status.rejected));
        analytics.put("Pending", referralService.countRecruiterReferrals(id, Status.requested));
        return analytics;
    }

    // ✅ Candidate success rate
    @GetMapping("/candidate/{id}/success")
    public Map<String, Object> getCandidateSuccessRate(@PathVariable int id) {
        long accepted = referralService.countCandidateReferrals(id, Status.accepted);
        long total = referralService.countCandidateReferrals(id, Status.requested)
                   + referralService.countCandidateReferrals(id, Status.accepted)
                   + referralService.countCandidateReferrals(id, Status.rejected);

        double successRate = total > 0 ? (accepted * 100.0 / total) : 0.0;

        Map<String, Object> result = new HashMap<>();
        result.put("CandidateId", id);
        result.put("Accepted", accepted);
        result.put("Total", total);
        result.put("SuccessRate", successRate);
        return result;
    }
}
