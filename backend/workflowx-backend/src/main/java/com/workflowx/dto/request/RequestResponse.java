package com.workflowx.dto.request;

import java.time.LocalDateTime;

import com.workflowx.enums.RequestStatus;
import com.workflowx.enums.RequestType;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RequestResponse {

    private String id;

    private String title;

    private String description;

    private RequestType requestType;

    private RequestStatus status;

    private String employeeEmail;

    private LocalDateTime submittedAt;

    private LocalDateTime approvedAt;

    private LocalDateTime rejectedAt;

    private String managerComments;
}