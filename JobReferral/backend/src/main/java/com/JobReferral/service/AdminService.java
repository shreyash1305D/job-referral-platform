package com.JobReferral.service;

import com.JobReferral.entities.Referral;
import com.JobReferral.entities.Status;
import com.JobReferral.repository.ReferralRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AdminService {
    
    @Autowired
    private ReferralRepository referralRepository;
    
    public List<Referral> getAllReferrals() {
        return referralRepository.findAll();
    }
    
    public List<Referral> getReferralsByStatus(Status status) {
        return referralRepository.findByStatus(status.toString());
    }
    
    public long getTotalReferrals() {
        return referralRepository.count();
    }
    
    public long getAcceptedReferrals() {
        return referralRepository.findByStatus(Status.ACCEPTED.toString()).size();
    }
    
    public long getPendingReferrals() {
        return referralRepository.findByStatus(Status.PENDING.toString()).size();
    }
    
    public long getRejectedReferrals() {
        return referralRepository.findByStatus(Status.REJECTED.toString()).size();
    }
}