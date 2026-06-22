package com.workflowx.dto.notification;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotificationResponse {

    private String id;

    private String title;

    private String message;

    private Boolean isRead;

    private LocalDateTime createdAt;
}
