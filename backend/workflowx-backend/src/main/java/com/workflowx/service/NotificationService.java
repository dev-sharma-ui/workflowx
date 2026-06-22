package com.workflowx.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.workflowx.dto.notification.NotificationResponse;
import com.workflowx.entity.Notification;
import com.workflowx.entity.User;
import com.workflowx.repository.NotificationRepository;
import com.workflowx.repository.UserRepository;
import com.workflowx.util.SecurityUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;

    public List<NotificationResponse> getMyNotifications() {

        String email =
                SecurityUtils.getCurrentUserEmail();

        User user =
                userRepository.findByEmail(email)
                        .orElseThrow(
                                () -> new RuntimeException(
                                        "User not found"
                                )
                        );

        return notificationRepository
                .findByRecipientIdOrderByCreatedAtDesc(
                        user.getId()
                )
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public List<NotificationResponse> getUnreadNotifications() {

        String email =
                SecurityUtils.getCurrentUserEmail();

        User user =
                userRepository.findByEmail(email)
                        .orElseThrow(
                                () -> new RuntimeException(
                                        "User not found"
                                )
                        );

        return notificationRepository
                .findByRecipientIdAndIsReadFalseOrderByCreatedAtDesc(
                        user.getId()
                )
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public NotificationResponse markAsRead(
            String notificationId
    ) {

        Notification notification =
                notificationRepository.findById(
                        notificationId
                ).orElseThrow(
                        () -> new RuntimeException(
                                "Notification not found"
                        )
                );

        notification.setIsRead(true);

        Notification savedNotification =
                notificationRepository.save(
                        notification
                ); 
        return mapToResponse(savedNotification);
    }

    public void createNotification(
        String recipientId,
        String title,
        String message
    ) {

    Notification notification =
            new Notification();

    notification.setRecipientId(
            recipientId
    );

    notification.setTitle(title);

    notification.setMessage(message);

    notification.setIsRead(false);

    notificationRepository.save(
            notification);
    }

    private NotificationResponse mapToResponse(
            Notification notification
    ) {

        NotificationResponse response =
                new NotificationResponse();

        response.setId(notification.getId());
        response.setTitle(notification.getTitle());
        response.setMessage(notification.getMessage());
        response.setIsRead(notification.getIsRead());

        response.setCreatedAt(
                notification.getCreatedAt()
        );

        return response;
    }
}