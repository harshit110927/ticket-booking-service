package com.booking.ticketbookingservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateMapRequest {
    @NotNull
    private Long tenantId;
    @NotBlank
    private String mapName;
    private String location;
}