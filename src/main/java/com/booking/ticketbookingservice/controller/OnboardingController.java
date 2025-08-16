package com.booking.ticketbookingservice.controller;

import com.booking.ticketbookingservice.dto.TenantOnboardingRequest;
import com.booking.ticketbookingservice.dto.TenantResponse;
import com.booking.ticketbookingservice.entity.Tenant;
import com.booking.ticketbookingservice.service.TenantOnboardingService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/onboarding")
public class OnboardingController {

    private final TenantOnboardingService onboardingService;

    public OnboardingController(TenantOnboardingService onboardingService) {
        this.onboardingService = onboardingService;
    }

    @PostMapping("/tenant")
    public ResponseEntity<TenantResponse> onboardTenant(@Valid @RequestBody TenantOnboardingRequest request) {
        Tenant tenant = onboardingService.onboardTenant(request);
        return ResponseEntity.ok(toTenantResponse(tenant));
    }

    private TenantResponse toTenantResponse(Tenant tenant) {
        TenantResponse dto = new TenantResponse();
        dto.setTenantId(tenant.getTenantId());
        dto.setTenantName(tenant.getTenantName());
        dto.setContactEmail(tenant.getContactEmail());
        dto.setCreatedAt(tenant.getCreatedAt());
        return dto;
    }
}