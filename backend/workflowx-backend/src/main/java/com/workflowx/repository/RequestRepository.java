package com.workflowx.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.workflowx.entity.Request;
import com.workflowx.entity.User;
import com.workflowx.enums.RequestStatus;
import com.workflowx.enums.RequestType;

public interface RequestRepository extends JpaRepository<Request, String> {

    // Existing methods
    List<Request> findByEmployee(User employee);

    // Dashboard analytics
    long countByStatus(RequestStatus status);

    long countByRequestType(RequestType requestType);

    @Query("""
            SELECT FUNCTION('TO_CHAR', r.submittedAt, 'Month'),
                   COUNT(r)
            FROM Request r
            GROUP BY FUNCTION('TO_CHAR', r.submittedAt, 'Month'),
                     FUNCTION('DATE_TRUNC', 'month', r.submittedAt)
            ORDER BY FUNCTION('DATE_TRUNC', 'month', r.submittedAt)
            """)
    List<Object[]> getMonthlyRequestCounts();
}
