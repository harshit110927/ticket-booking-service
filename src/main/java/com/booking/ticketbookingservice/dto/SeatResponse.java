package com.booking.ticketbookingservice.dto;

import lombok.Data;

@Data
public class SeatResponse {
    private Integer seatId;
    private String rowIdentifier;
    private String seatNumber;
}