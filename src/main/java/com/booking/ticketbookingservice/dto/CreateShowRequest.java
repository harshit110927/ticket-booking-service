package com.booking.ticketbookingservice.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateShowRequest {
    @NotBlank
    private String title;
    private String description;
    private Integer durationInMinutes;
}