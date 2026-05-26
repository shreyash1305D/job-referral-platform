package com.JobReferral.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.JobReferral.entities.Job;
import com.JobReferral.entities.User;
import com.JobReferral.repository.JobRepository;
import com.JobReferral.repository.UserRepository;

import java.util.List;

@Service
public class JobService {

    @Autowired
    private JobRepository jobRepository;
    
    @Autowired
    private UserRepository userRepository;

    public Job createJob(Job job) {
        if(job.getRecruiter() != null && job.getRecruiter().getId() != 0) {
            User recruiter = userRepository.findById(job.getRecruiter().getId())
                    .orElseThrow(() -> new RuntimeException("Recruiter not found"));
            job.setRecruiter(recruiter);
        }
        return jobRepository.save(job);
    }

    public List<Job> getAllJobs() {
        return jobRepository.findAll();
    }

    public Job getJob(int id) {
        return jobRepository.findById(id).orElse(null);
    }
}
