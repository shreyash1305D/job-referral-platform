package com.JobReferral.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.JobReferral.entities.Application;

public interface ApplicationRepository extends JpaRepository<Application, Integer> {
}

