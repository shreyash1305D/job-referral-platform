package com.JobReferral.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.JobReferral.entities.Notification;
import com.JobReferral.entities.User;
import com.JobReferral.repository.NotificationRepository;
import com.JobReferral.repository.UserRepository;

import java.util.List;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;
    @Autowired
    private UserRepository userRepository;

    public Notification createNotification(Notification notification) {
        User user = userRepository.findById(notification.getUser().getId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        notification.setUser(user);
        return notificationRepository.save(notification);
    }

    public List<Notification> getUserNotifications(int userId) {
        return notificationRepository.findByUserId(userId);
    }

    public Notification markAsRead(int id) {
        Notification notification = notificationRepository.findById(id).orElse(null);
        if (notification != null) {
            notification.setStatus(Notification.Status.read);
            return notificationRepository.save(notification);
        }
        return null;
    }
}

