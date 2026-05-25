package com.JobReferral.entities;


import jakarta.persistence.*;

@Entity
@Table(name = "applications")
public class Application {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "referral_id")
    private Referral referral;

    private String interviewDate;

    @Enumerated(EnumType.STRING)
    private FinalStatus finalStatus;

    public enum FinalStatus {
        pending, selected, rejected
    }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Referral getReferral() {
		return referral;
	}

	public void setReferral(Referral referral) {
		this.referral = referral;
	}

	public String getInterviewDate() {
		return interviewDate;
	}

	public void setInterviewDate(String interviewDate) {
		this.interviewDate = interviewDate;
	}

	public FinalStatus getFinalStatus() {
		return finalStatus;
	}

	public void setFinalStatus(FinalStatus finalStatus) {
		this.finalStatus = finalStatus;
	}
	
    
}

