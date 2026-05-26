package com.JobReferral.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.JobReferral.entities.Notification;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Integer> {
    List<com.JobReferral.entities.Notification> findByUserId(int userId);
}

