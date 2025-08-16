package com.booking.ticketbookingservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateSectionRequest {
    @NotNull
    private Integer mapId;
    @NotBlank
    private String sectionName;
}