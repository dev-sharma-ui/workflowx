package com.workflowx.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.workflowx.dto.dashboard.DashboardSummaryResponse;
import com.workflowx.dto.dashboard.MonthlyAnalyticsResponse;
import com.workflowx.dto.dashboard.RequestTypeAnalyticsResponse;
import com.workflowx.service.DashboardService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping("/summary")
    public DashboardSummaryResponse getDashboardSummary() {

        return dashboardService.getDashboardSummary();
    }

    @GetMapping("/request-types")
    public List<RequestTypeAnalyticsResponse> getRequestTypeAnalytics() {

        return dashboardService.getRequestTypeAnalytics();
    }

    @GetMapping("/monthly")
    public List<MonthlyAnalyticsResponse> getMonthlyAnalytics() {

        return dashboardService.getMonthlyAnalytics();
    }
}
