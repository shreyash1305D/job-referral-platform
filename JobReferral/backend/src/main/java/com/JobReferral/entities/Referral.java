package com.JobReferral.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "referrals")
public class Referral {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    // ✅ Relation with Job
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_id", nullable = false)
    private Job job;

    // ✅ Candidate (User applying for referral)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "candidate_id", nullable = false)
    private User candidate;

    // ✅ Employee (User giving referral)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", nullable = false)
    private User employee;

    // ✅ Referral status
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status = Status.requested; // default value

    // ✅ Enum for referral status
    public enum Status {
        requested,
        accepted,
        rejected
    }

    // ✅ Getters & Setters
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public Job getJob() {
        return job;
    }
    public void setJob(Job job) {
        this.job = job;
    }

    public User getCandidate() {
        return candidate;
    }
    public void setCandidate(User candidate) {
        this.candidate = candidate;
    }

    public User getEmployee() {
        return employee;
    }
    public void setEmployee(User employee) {
        this.employee = employee;
    }

    public Status getStatus() {
        return status;
    }
    public void setStatus(Status status) {
        this.status = status;
    }
}
