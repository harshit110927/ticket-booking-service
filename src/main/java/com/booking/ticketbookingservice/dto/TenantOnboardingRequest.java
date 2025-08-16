package com.booking.ticketbookingservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class TenantOnboardingRequest {

    // Tenant Details
    @NotBlank
    private String tenantName;

    // Admin User Details
    @NotBlank
    private String adminUserName;

    @NotBlank
    @Email
    private String adminEmail;

    @NotBlank
    @Size(min = 8)
    private String adminPassword;
}