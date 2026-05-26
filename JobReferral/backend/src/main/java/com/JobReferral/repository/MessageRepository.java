package com.JobReferral.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.JobReferral.entities.Message;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Integer> {
    List<Message> findBySenderId(int senderId);
    List<Message> findByReceiverId(int receiverId);
}

