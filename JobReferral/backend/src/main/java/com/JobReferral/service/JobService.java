package com.JobReferral.service;

import com.JobReferral.entities.JobPosting;
import com.JobReferral.entities.User;
import com.JobReferral.repository.JobPostingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class JobService {
    
    @Autowired
    private JobPostingRepository jobPostingRepository;
    
    public JobPosting createJobPosting(JobPosting job, User recruiter) {
        job.setRecruiter(recruiter);
        job.setCreatedAt(LocalDateTime.now());
        job.setUpdatedAt(LocalDateTime.now());
        return jobPostingRepository.save(job);
    }
    
    public List<JobPosting> getAllActiveJobs() {
        return jobPostingRepository.findByIsActiveTrue();
    }
    
    public List<JobPosting> getJobsByRecruiter(Long recruiterId) {
        return jobPostingRepository.findByRecruiterId(recruiterId);
    }
    
    public Optional<JobPosting> getJobById(Long id) {
        return jobPostingRepository.findById(id);
    }
    
    public JobPosting updateJobPosting(Long id, JobPosting updatedJob) {
        Optional<JobPosting> job = jobPostingRepository.findById(id);
        if (job.isPresent()) {
            JobPosting existing = job.get();
            existing.setJobTitle(updatedJob.getJobTitle());
            existing.setDescription(updatedJob.getDescription());
            existing.setLocation(updatedJob.getLocation());
            existing.setSalary(updatedJob.getSalary());
            existing.setJobType(updatedJob.getJobType());
            existing.setExperience(updatedJob.getExperience());
            existing.setRequirements(updatedJob.getRequirements());
            existing.setBenefits(updatedJob.getBenefits());
            existing.setUpdatedAt(LocalDateTime.now());
            return jobPostingRepository.save(existing);
        }
        return null;
    }
    
    public boolean deleteJobPosting(Long id) {
        Optional<JobPosting> job = jobPostingRepository.findById(id);
        if (job.isPresent()) {
            jobPostingRepository.deleteById(id);
            return true;
        }
        return false;
    }
}