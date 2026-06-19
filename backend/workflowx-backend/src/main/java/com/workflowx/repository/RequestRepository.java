package com.workflowx.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.workflowx.entity.Request;

public interface RequestRepository extends JpaRepository<Request, String> {
}