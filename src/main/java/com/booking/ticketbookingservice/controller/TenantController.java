package com.booking.ticketbookingservice.controller;

import com.booking.ticketbookingservice.dto.CreateTenantRequest;
import com.booking.ticketbookingservice.dto.TenantResponse;
import com.booking.ticketbookingservice.entity.Tenant;
import com.booking.ticketbookingservice.service.TenantService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/tenants")
public class TenantController {

    private final TenantService tenantService;

    public TenantController(TenantService tenantService) {
        this.tenantService = tenantService;
    }

    @PostMapping
    public ResponseEntity<TenantResponse> createTenant(@Valid @RequestBody CreateTenantRequest request) {
        Tenant newTenant = new Tenant();
        newTenant.setTenantName(request.getTenantName());
        newTenant.setContactEmail(request.getContactEmail());
        Tenant createdTenant = tenantService.createTenant(newTenant);
        return ResponseEntity.ok(toTenantResponse(createdTenant));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TenantResponse> getTenantById(@PathVariable Long id) {
        Tenant tenant = tenantService.getTenantById(id);
        return ResponseEntity.ok(toTenantResponse(tenant));
    }

    @GetMapping
    public ResponseEntity<List<TenantResponse>> getAllTenants() {
        List<Tenant> tenants = tenantService.getAllTenants();
        List<TenantResponse> tenantResponses = tenants.stream()
                .map(this::toTenantResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(tenantResponses);
    }

    // Helper method to convert Entity to DTO
    private TenantResponse toTenantResponse(Tenant tenant) {
        TenantResponse dto = new TenantResponse();
        dto.setTenantId(tenant.getTenantId());
        dto.setTenantName(tenant.getTenantName());
        dto.setContactEmail(tenant.getContactEmail());
        dto.setCreatedAt(tenant.getCreatedAt());
        return dto;
    }
}