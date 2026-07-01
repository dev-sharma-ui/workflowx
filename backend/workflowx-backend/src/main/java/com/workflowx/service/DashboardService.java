package com.workflowx.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.workflowx.dto.dashboard.DashboardSummaryResponse;
import com.workflowx.dto.dashboard.MonthlyAnalyticsResponse;
import com.workflowx.dto.dashboard.RequestTypeAnalyticsResponse;
import com.workflowx.enums.RequestStatus;
import com.workflowx.enums.RequestType;
import com.workflowx.repository.RequestRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final RequestRepository requestRepository;

    @PreAuthorize("hasAnyRole('MANAGER','ADMIN')")
    public DashboardSummaryResponse getDashboardSummary() {

        DashboardSummaryResponse response =
                new DashboardSummaryResponse();

        long total =
                requestRepository.count();

        long approved =
                requestRepository.countByStatus(
                        RequestStatus.APPROVED
                );

        long rejected =
                requestRepository.countByStatus(
                        RequestStatus.REJECTED
                );

        long pending =
                requestRepository.countByStatus(
                        RequestStatus.PENDING
                );

        long cancelled =
                requestRepository.countByStatus(
                        RequestStatus.CANCELLED
                );

        response.setTotalRequests(total);
        response.setApprovedRequests(approved);
        response.setRejectedRequests(rejected);
        response.setPendingRequests(pending);
        response.setCancelledRequests(cancelled);

        double approvalRate = 0;

        if (total > 0) {
            approvalRate =
                    (approved * 100.0) / total;
        }

        response.setApprovalRate(
                approvalRate
        );

        return response;
    }

    @PreAuthorize("hasAnyRole('MANAGER','ADMIN')")
    public List<RequestTypeAnalyticsResponse> getRequestTypeAnalytics() {

        List<RequestTypeAnalyticsResponse> analytics =
                new ArrayList<>();

        for (RequestType type : RequestType.values()) {

            analytics.add(

                    new RequestTypeAnalyticsResponse(

                            type,

                            requestRepository
                                    .countByRequestType(type)
                    )

            );
        }

        return analytics;
    }

    @PreAuthorize("hasAnyRole('MANAGER','ADMIN')")
    public List<MonthlyAnalyticsResponse> getMonthlyAnalytics() {

        List<MonthlyAnalyticsResponse> analytics =
                new ArrayList<>();

        List<Object[]> rows =
                requestRepository
                        .getMonthlyRequestCounts();

        for (Object[] row : rows) {

            analytics.add(

                    new MonthlyAnalyticsResponse(

                            row[0].toString().trim(),

                            ((Long) row[1])

                    )

            );
        }

        return analytics;
    }
}