package com.workflowx.dto.dashboard;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DashboardSummaryResponse {

    private long totalRequests;

    private long pendingRequests;

    private long approvedRequests;

    private long rejectedRequests;

    private long cancelledRequests;

    private double approvalRate;
}
