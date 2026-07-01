package com.workflowx.dto.dashboard;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MonthlyAnalyticsResponse {

    private String month;

    private long count;
}