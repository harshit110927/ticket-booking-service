package com.booking.ticketbookingservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateSeatRequest {
    @NotNull
    private Integer sectionId;
    @NotBlank
    private String rowIdentifier;
    @NotBlank
    private String seatNumber;
}