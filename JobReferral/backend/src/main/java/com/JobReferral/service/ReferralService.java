package com.JobReferral.service;

import com.JobReferral.entities.Referral;
import com.JobReferral.entities.Status;
import com.JobReferral.repository.ReferralRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ReferralService {
    
    @Autowired
    private ReferralRepository referralRepository;
    
    public Referral createReferral(Referral referral) {
        referral.setStatus(Status.PENDING);
        referral.setCreatedAt(LocalDateTime.now());
        referral.setUpdatedAt(LocalDateTime.now());
        return referralRepository.save(referral);
    }
    
    public List<Referral> getReferralsByJobId(Long jobId) {
        return referralRepository.findByJobPostingId(jobId);
    }
    
    public List<Referral> getReferralsByReferrerId(Long referrerId) {
        return referralRepository.findByReferrerId(referrerId);
    }
    
    public List<Referral> getReferralsByStatus(String status) {
        return referralRepository.findByStatus(status);
    }
    
    public Referral updateReferralStatus(Long id, String status) {
        Optional<Referral> referral = referralRepository.findById(id);
        if (referral.isPresent()) {
            Referral existing = referral.get();
            try {
                existing.setStatus(Status.valueOf(status.toUpperCase()));
                existing.setUpdatedAt(LocalDateTime.now());
                return referralRepository.save(existing);
            } catch (IllegalArgumentException e) {
                return null;
            }
        }
        return null;
    }
    
    public Optional<Referral> getReferralById(Long id) {
        return referralRepository.findById(id);
    }
}