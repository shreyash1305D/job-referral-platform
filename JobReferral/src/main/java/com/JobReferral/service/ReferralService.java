package com.JobReferral.service;

import com.JobReferral.entities.Job;
import com.JobReferral.entities.Referral;
import com.JobReferral.entities.Referral.Status;
import com.JobReferral.entities.User;
import com.JobReferral.repository.JobRepository;
import com.JobReferral.repository.ReferralRepository;
import com.JobReferral.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReferralService {

    @Autowired
    private ReferralRepository referralRepository;
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JobRepository jobRepository;
    
    // ✅ Candidate requests a referral
    public Referral requestReferral(Referral referral) {
        User candidate = userRepository.findById(referral.getCandidate().getId())
                .orElseThrow(() -> new RuntimeException("Candidate not found"));
        User employee = userRepository.findById(referral.getEmployee().getId())
                .orElseThrow(() -> new RuntimeException("Employee not found"));
        Job job = jobRepository.findById(referral.getJob().getId())
                .orElseThrow(() -> new RuntimeException("Job not found"));

        referral.setCandidate(candidate);
        referral.setEmployee(employee);
        referral.setJob(job);

        return referralRepository.save(referral);
    }

    // ✅ HR/Employee updates referral status
    public Referral updateStatus(int id, Status status) {
        return referralRepository.findById(id)
                .map(ref -> {
                    ref.setStatus(status);
                    return referralRepository.save(ref);
                })
                .orElse(null);
    }

    // ✅ Get referrals requested by candidate
    public List<Referral> getCandidateReferrals(int candidateId) {
        return referralRepository.findByCandidateId(candidateId);
    }

    // ✅ Get referrals assigned to employee
    public List<Referral> getEmployeeReferrals(int employeeId) {
        return referralRepository.findByEmployeeId(employeeId);
    }

    // ✅ Get pending referrals (requested only)
    public List<Referral> getPendingReferrals() {
        return referralRepository.findByStatus(Status.requested);
    }

    // ✅ Count referrals by status (for analytics)
    public long countByStatus(Status status) {
        return referralRepository.countByStatus(status);
    }
 // Recruiter-wise referral counts
    public long countRecruiterReferrals(int recruiterId, Status status) {
        return referralRepository.countByJobRecruiterIdAndStatus(recruiterId, status);
    }

    // Candidate-wise referral counts
    public long countCandidateReferrals(int candidateId, Status status) {
        return referralRepository.countByCandidateIdAndStatus(candidateId, status);
    }

}
