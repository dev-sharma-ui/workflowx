package com.workflowx.service;

import org.springframework.stereotype.Service;

import com.workflowx.entity.AuditLog;
import com.workflowx.enums.AuditAction;
import com.workflowx.repository.AuditLogRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuditService {

    private final AuditLogRepository auditLogRepository;

    public void logAction(
            String userId,
            String userEmail,
            AuditAction action,
            String entityType,
            String entityId,
            String details
    ) {

        AuditLog auditLog = new AuditLog();

        auditLog.setUserId(userId);
        auditLog.setUserEmail(userEmail);

        auditLog.setAction(action);

        auditLog.setEntityType(entityType);
        auditLog.setEntityId(entityId);

        auditLog.setDetails(details);

        auditLogRepository.save(auditLog);
    }
}