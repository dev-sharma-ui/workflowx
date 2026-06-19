package com.workflowx.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateRequestStatusRequest {

    @NotBlank
    private String managerComments;
}