package com.booking.ticketbookingservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreatePricingTierRequest {
    @NotNull
    private Long tenantId; // The ID of the tenant who owns this tier

    @NotBlank
    private String tierName; // e.g., "Standard", "VIP", "Balcony"
}