package com.workflowx.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.workflowx.dto.notification.NotificationResponse;
import com.workflowx.service.NotificationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping
    public List<NotificationResponse> getMyNotifications() {

        return notificationService.getMyNotifications();
    }

    @GetMapping("/unread")
    public List<NotificationResponse> getUnreadNotifications() {

        return notificationService.getUnreadNotifications();
    }

    @PutMapping("/{notificationId}/read")
    public NotificationResponse markAsRead(
            @PathVariable String notificationId
    ) {

        return notificationService.markAsRead(
                notificationId
        );
    }
}