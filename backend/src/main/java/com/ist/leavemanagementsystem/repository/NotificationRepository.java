package com.ist.leavemanagementsystem.repository;

import com.ist.leavemanagementsystem.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    // Find all notifications for a specific recipient
    List<Notification> findByRecipientId(Long recipientId);

    // Find all unread notifications for a specific recipient
    List<Notification> findByRecipientIdAndReadFalse(Long recipientId);

    // Mark all notifications as read for a specific recipient
    default void markAllAsRead(Long recipientId) {
        List<Notification> notifications = findByRecipientIdAndReadFalse(recipientId);
        notifications.forEach(notification -> notification.setRead(true));
        saveAll(notifications);
    }
}
