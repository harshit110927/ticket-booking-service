package com.booking.ticketbookingservice.dto;

import lombok.Data;
import java.time.Instant;

@Data
public class TenantResponse {
    private Long tenantId;
    private String tenantName;
    private String contactEmail;
    private Instant createdAt;
}