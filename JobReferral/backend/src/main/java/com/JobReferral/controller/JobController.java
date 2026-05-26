package com.JobReferral.controller;

import com.JobReferral.entities.JobPosting;
import com.JobReferral.entities.User;
import com.JobReferral.service.AuthService;
import com.JobReferral.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/jobs")
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:3000"})
public class JobController {
    
    @Autowired
    private JobService jobService;
    
    @Autowired
    private AuthService authService;
    
    @GetMapping
    public ResponseEntity<?> getAllJobs() {
        List<JobPosting> jobs = jobService.getAllActiveJobs();
        return ResponseEntity.ok(jobs);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> getJobById(@PathVariable Long id) {
        Optional<JobPosting> job = jobService.getJobById(id);
        if (job.isPresent()) {
            return ResponseEntity.ok(job.get());
        }
        return ResponseEntity.notFound().build();
    }
    
    @PostMapping
    public ResponseEntity<?> createJob(@RequestBody JobPosting job) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<User> user = authService.getUserByEmail(email);
        
        if (user.isPresent()) {
            JobPosting created = jobService.createJobPosting(job, user.get());
            return ResponseEntity.ok(created);
        }
        return ResponseEntity.badRequest().body("User not found");
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> updateJob(@PathVariable Long id, @RequestBody JobPosting job) {
        JobPosting updated = jobService.updateJobPosting(id, job);
        if (updated != null) {
            return ResponseEntity.ok(updated);
        }
        return ResponseEntity.notFound().build();
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteJob(@PathVariable Long id) {
        if (jobService.deleteJobPosting(id)) {
            return ResponseEntity.ok("Job deleted successfully");
        }
        return ResponseEntity.notFound().build();
    }
    
    @GetMapping("/recruiter/list")
    public ResponseEntity<?> getRecruiterJobs() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<User> user = authService.getUserByEmail(email);
        
        if (user.isPresent()) {
            List<JobPosting> jobs = jobService.getJobsByRecruiter(user.get().getId());
            return ResponseEntity.ok(jobs);
        }
        return ResponseEntity.badRequest().body("User not found");
    }
}