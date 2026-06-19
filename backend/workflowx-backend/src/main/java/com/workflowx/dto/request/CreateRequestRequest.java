package com.workflowx.dto.request;

import com.workflowx.enums.RequestType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateRequestRequest {

    @NotBlank
    private String title;

    @NotBlank
    private String description;

    @NotNull
    private RequestType requestType;
}