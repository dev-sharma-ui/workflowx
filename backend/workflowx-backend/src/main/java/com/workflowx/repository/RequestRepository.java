package com.workflowx.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.workflowx.entity.Request;
import com.workflowx.entity.User;
import com.workflowx.enums.RequestStatus;

public interface RequestRepository extends JpaRepository<Request, String> {

    List<Request> findByEmployee(User employee);

    List<Request> findByStatus(RequestStatus status);
}