package com.workflowx.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.workflowx.dto.request.CreateRequestRequest;
import com.workflowx.dto.request.RequestResponse;
import com.workflowx.dto.request.UpdateRequestStatusRequest;
import com.workflowx.service.RequestService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/requests")
@RequiredArgsConstructor
public class RequestController {

    private final RequestService requestService;

    @PostMapping
    public RequestResponse createRequest(
            @Valid @RequestBody CreateRequestRequest request
    ) {
        return requestService.createRequest(request);
    }

    @GetMapping("/my")
    public List<RequestResponse> getMyRequests() {
        return requestService.getMyRequests();
    }

    @GetMapping("/{requestId}")
    public RequestResponse getRequestById(
            @PathVariable String requestId
    ) {
        return requestService.getRequestById(requestId);
    }

    @PutMapping("/{requestId}/approve")
    public RequestResponse approveRequest(
            @PathVariable String requestId,
            @Valid @RequestBody UpdateRequestStatusRequest request
    ) {
        return requestService.approveRequest(
                requestId,
                request
        );
    }

    @PutMapping("/{requestId}/reject")
    public RequestResponse rejectRequest(
            @PathVariable String requestId,
            @Valid @RequestBody UpdateRequestStatusRequest request
    ) {
        return requestService.rejectRequest(
                requestId,
                request
        );
    }

    @PutMapping("/{requestId}/cancel")
    public RequestResponse cancelRequest(
            @PathVariable String requestId
    ) {
        return requestService.cancelRequest(requestId);
    }
}