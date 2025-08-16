package com.booking.ticketbookingservice.dto;

import com.booking.ticketbookingservice.entity.TicketStatus;
import lombok.Data;
import java.math.BigDecimal;
import java.time.Instant;

@Data
public class TicketResponse {
    private Integer ticketId;
    private BigDecimal price;
    private TicketStatus status;
    private Instant lockedUntil;
    private EventSummaryDto event;
    private SeatResponse seat;
}