package com.JobReferral.service;

import com.JobReferral.repository.UserRepository;
import com.JobReferral.repository.JobRepository;
import com.JobReferral.repository.ReferralRepository;
import com.JobReferral.entities.Referral.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private ReferralRepository referralRepository;

    public long getUserCount() {
        return userRepository.count();
    }

    public long getJobCount() {
        return jobRepository.count();
    }

    public long getReferralCount() {
        return referralRepository.count();
    }

    public long getRecruiterJobCount(int recruiterId) {
        return jobRepository.countByRecruiterId(recruiterId);
    }

    public long getRecruiterReferralCount(int recruiterId, Status status) {
        return referralRepository.countByJobRecruiterIdAndStatus(recruiterId, status);
    }
}
