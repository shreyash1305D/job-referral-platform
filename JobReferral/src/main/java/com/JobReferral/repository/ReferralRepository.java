package com.JobReferral.repository;

import com.JobReferral.entities.Referral;
import com.JobReferral.entities.Referral.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ReferralRepository extends JpaRepository<Referral, Integer> {

    // ✅ Candidate-wise referrals
    List<Referral> findByCandidateId(int candidateId);
    long countByCandidateIdAndStatus(int candidateId, Status status);

    // ✅ Employee-wise referrals
    List<Referral> findByEmployeeId(int employeeId);

    // ✅ Overall referrals by status
    long countByStatus(Status status);
    List<Referral> findByStatus(Status status);

    // ✅ Recruiter-wise referrals
    long countByJobRecruiterIdAndStatus(int recruiterId, Status status);

    // ✅ Extra: recruiter-wise all referrals
    List<Referral> findByJobRecruiterId(int recruiterId);
}
