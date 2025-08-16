package com.booking.ticketbookingservice.dto;

import lombok.Data;

@Data
public class ShowResponse {
    private Integer showId;
    private String title;
    private String description;
    private Integer durationInMinutes;
}