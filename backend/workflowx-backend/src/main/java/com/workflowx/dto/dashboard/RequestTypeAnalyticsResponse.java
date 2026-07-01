package com.workflowx.dto.dashboard;

import com.workflowx.enums.RequestType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RequestTypeAnalyticsResponse {

    private RequestType requestType;

    private long count;
}