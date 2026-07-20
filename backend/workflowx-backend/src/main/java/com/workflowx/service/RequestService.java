package com.workflowx.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.workflowx.dto.request.CreateRequestRequest;
import com.workflowx.dto.request.RequestResponse;
import com.workflowx.dto.request.UpdateRequestStatusRequest;
import com.workflowx.entity.Request;
import com.workflowx.entity.User;
import com.workflowx.enums.AuditAction;
import com.workflowx.enums.RequestStatus;
import com.workflowx.exception.BadRequestException;
import com.workflowx.exception.ResourceNotFoundException;
import com.workflowx.exception.UnauthorizedException;
import com.workflowx.repository.RequestRepository;
import com.workflowx.repository.UserRepository;
import com.workflowx.util.SecurityUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RequestService {

    private final RequestRepository requestRepository;
    private final UserRepository userRepository;
    private final AuditService auditService;
    private final NotificationService notificationService;

    @PreAuthorize("hasRole('EMPLOYEE')")
    public RequestResponse createRequest(
            CreateRequestRequest requestDto
    ) {

        String email =
                SecurityUtils.getCurrentUserEmail();

        User employee =
                userRepository.findByEmail(email)
                        .orElseThrow(
        () -> new ResourceNotFoundException(
                "User not found"
        )
);      

        Request request = new Request();

        request.setTitle(requestDto.getTitle());
        request.setDescription(
                requestDto.getDescription()
        );
        request.setRequestType(
                requestDto.getRequestType()
        );

        request.setStatus(RequestStatus.PENDING);

        request.setEmployee(employee);

        request.setSubmittedAt(
                LocalDateTime.now()
        );

        Request savedRequest =
                requestRepository.save(request);

        auditService.logAction(
        employee.getId(),
        employee.getEmail(),
        AuditAction.REQUEST_CREATED,
        "REQUEST",
        savedRequest.getId(),
        "Request created: " + savedRequest.getTitle()
        );
        List<User> managers =
        userRepository.findByRole(
                com.workflowx.enums.Role.MANAGER
        );

        for (User manager : managers) {

            notificationService.createNotification(
            manager.getId(),
            "New Request Submitted",
            employee.getFullName()
                    + " submitted a "
                    + request.getRequestType()
                    + " request"
                 );
        }
        return mapToResponse(savedRequest);
    }

    @PreAuthorize("hasRole('EMPLOYEE')")
    public List<RequestResponse> getMyRequests() {

        String email =
                SecurityUtils.getCurrentUserEmail();

        User employee =
                userRepository.findByEmail(email)
                        .orElseThrow(
        () -> new ResourceNotFoundException(
                "User not found"
        )
);

        return requestRepository
                .findByEmployee(employee)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }


    public RequestResponse getRequestById(
            String requestId
    ) {

        Request request =
                requestRepository.findById(requestId)
                        .orElseThrow(
        () -> new ResourceNotFoundException(
                "Request not found"
        )
);

        return mapToResponse(request);
    }

    @PreAuthorize("hasRole('MANAGER')")
    public RequestResponse approveRequest(
            String requestId,
            UpdateRequestStatusRequest dto
    ) {

        Request request =
                requestRepository.findById(requestId)
                        .orElseThrow(
        () -> new ResourceNotFoundException(
                "Request not found"
        )
);

        validatePendingRequest(request);

        request.setStatus(RequestStatus.APPROVED);

        request.setApprovedAt(
                LocalDateTime.now()
        );

        request.setManagerComments(
                dto.getManagerComments()
        );

        Request savedRequest =
                requestRepository.save(request);
        String managerEmail =
        SecurityUtils.getCurrentUserEmail();

        User manager =
                userRepository.findByEmail(managerEmail)
                .orElseThrow(
        () -> new ResourceNotFoundException(
                "Manager not found"
        )
);

        auditService.logAction(
                manager.getId(),
                manager.getEmail(),
                AuditAction.REQUEST_APPROVED,
                "REQUEST",
                savedRequest.getId(),
                "Request approved: " + savedRequest.getTitle()
        );
        notificationService.createNotification(
        request.getEmployee().getId(),
        "Request Approved",
        "Your request '"
                + request.getTitle()
                + "' has been approved"
        );
        return mapToResponse(savedRequest);
    }

    @PreAuthorize("hasRole('MANAGER')")
    public RequestResponse rejectRequest(
            String requestId,
            UpdateRequestStatusRequest dto
    ) {

        Request request =
                requestRepository.findById(requestId)
                        .orElseThrow(
        () -> new ResourceNotFoundException(
                "Request not found"
        )
);

        validatePendingRequest(request);

        request.setStatus(RequestStatus.REJECTED);

        request.setRejectedAt(
                LocalDateTime.now()
        );

        request.setManagerComments(
                dto.getManagerComments()
        );

        Request savedRequest =
                requestRepository.save(request);
        String managerEmail =
        SecurityUtils.getCurrentUserEmail();

        User manager =
                userRepository.findByEmail(managerEmail)
                .orElseThrow(
                        () -> new ResourceNotFoundException(
                                "Manager not found"
                        )
                );

        auditService.logAction(
        manager.getId(),
        manager.getEmail(),
        AuditAction.REQUEST_REJECTED,
        "REQUEST",
        savedRequest.getId(),
        "Request rejected: " + savedRequest.getTitle()
        );
        notificationService.createNotification(
        request.getEmployee().getId(),
        "Request Rejected",
        "Your request '"
                + request.getTitle()
                + "' has been rejected"
        );
        return mapToResponse(savedRequest);
    }

    @PreAuthorize("hasRole('EMPLOYEE')")
    public RequestResponse cancelRequest(
            String requestId
    ) {

        String email =
                SecurityUtils.getCurrentUserEmail();

        Request request =
                requestRepository.findById(requestId)
                        .orElseThrow(
        () -> new ResourceNotFoundException(
                "Request not found"
        )
);

        if (!request.getEmployee()
                .getEmail()
                .equals(email)) {

            throw new UnauthorizedException(
        "You can only cancel your own requests"
);      
        }

        validatePendingRequest(request);

        request.setStatus(
                RequestStatus.CANCELLED
        );

        Request savedRequest =
                requestRepository.save(request);
        User employee =
        userRepository.findByEmail(email)
                .orElseThrow(
        () -> new ResourceNotFoundException(
                "User not found"
        )
);

        auditService.logAction(
        employee.getId(),
        employee.getEmail(),
        AuditAction.REQUEST_CANCELLED,
        "REQUEST",
        savedRequest.getId(),
        "Request cancelled: " + savedRequest.getTitle()
        );
        List<User> managers =
        userRepository.findByRole(
                com.workflowx.enums.Role.MANAGER
        );

for (User manager : managers) {

    notificationService.createNotification(
            manager.getId(),
            "Request Cancelled",
            employee.getFullName()
                    + " cancelled request '"
                    + request.getTitle()
                    + "'"
    );
}
        return mapToResponse(savedRequest);
    }

    
    private void validatePendingRequest(
            Request request
    ) {

        if (request.getStatus()
                != RequestStatus.PENDING) {

            throw new BadRequestException(
                "Request is already finalized"
                );
        }
    }

    private RequestResponse mapToResponse(
            Request request
    ) {

        return RequestResponse.builder()
                .id(request.getId())
                .title(request.getTitle())
                .description(
                        request.getDescription()
                )
                .requestType(
                        request.getRequestType()
                )
                .status(
                        request.getStatus()
                )
                .employeeEmail(
                        request.getEmployee()
                                .getEmail()
                )
                .submittedAt(
                        request.getSubmittedAt()
                )
                .approvedAt(
                        request.getApprovedAt()
                )
                .rejectedAt(
                        request.getRejectedAt()
                )
                .managerComments(
                        request.getManagerComments()
                )
                .build();
    }
}