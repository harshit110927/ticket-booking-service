package com.booking.ticketbookingservice.dto;

import lombok.Data;

@Data
public class PricingTierResponse {
    private Integer tierId;
    private String tierName;
    private Long tenantId;
}