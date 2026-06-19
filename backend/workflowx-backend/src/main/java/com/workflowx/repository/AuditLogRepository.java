package com.workflowx.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.workflowx.entity.AuditLog;

public interface AuditLogRepository extends JpaRepository<AuditLog, String> {
}