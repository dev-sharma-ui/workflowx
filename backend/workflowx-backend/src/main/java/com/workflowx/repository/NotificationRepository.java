package com.workflowx.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.workflowx.entity.Notification;

public interface NotificationRepository
        extends JpaRepository<Notification, String> {

    List<Notification> findByRecipientIdOrderByCreatedAtDesc(
            String recipientId
    );

    List<Notification> findByRecipientIdAndIsReadFalseOrderByCreatedAtDesc(
            String recipientId
    );
}