package com.JobReferral.controller;

import com.JobReferral.entities.Referral;
import com.JobReferral.entities.Referral.Status;
import com.JobReferral.repository.ReferralRepository;
import com.JobReferral.service.ReferralService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/referrals")
public class ReferralController {

    @Autowired
    private ReferralService referralService;

    @Autowired
    private ReferralRepository referralRepository;

    
    @PostMapping
    public Referral requestReferral(@RequestBody Referral referral) {
        return referralService.requestReferral(referral);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<?> updateStatus(@PathVariable int id, @RequestParam Status status) {
        Referral updated = referralService.updateStatus(id, status);
        return updated != null
                ? ResponseEntity.ok(updated)
                : ResponseEntity.notFound().build();
    }

    @GetMapping("/candidate/{id}")
    public List<Referral> getCandidateReferrals(@PathVariable int id) {
        return referralService.getCandidateReferrals(id);
    }

    @GetMapping("/employee/{id}")
    public List<Referral> getEmployeeReferrals(@PathVariable int id) {
        return referralService.getEmployeeReferrals(id);
    }

    @GetMapping("/pending")
    public List<Referral> getPendingReferrals() {
        return referralRepository.findByStatus(Status.requested);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateReferralStatus(@PathVariable int id, @RequestBody Referral updatedReferral) {
        return referralRepository.findById(id)
                .map(referral -> {
                    referral.setStatus(updatedReferral.getStatus());
                    referralRepository.save(referral);
                    return ResponseEntity.ok("Referral " + updatedReferral.getStatus());
                })
                .orElse(ResponseEntity.notFound().build());
    }
    @GetMapping("/{id}")
    public ResponseEntity<Referral> getReferralById(@PathVariable int id) {
        return referralRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

}
