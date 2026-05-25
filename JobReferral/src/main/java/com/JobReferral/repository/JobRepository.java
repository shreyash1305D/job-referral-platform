package com.JobReferral.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.JobReferral.entities.Job;

public interface JobRepository extends JpaRepository<Job, Integer> {
	 long countByRecruiterId(int recruiterId);

	    // Recruiter-wise job list
	    java.util.List<Job> findByRecruiterId(int recruiterId);
	    List<Job> findByRecruiter_Id(int recruiterId);
}
