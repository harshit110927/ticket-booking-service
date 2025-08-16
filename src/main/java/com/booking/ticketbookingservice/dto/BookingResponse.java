package com.booking.ticketbookingservice.dto;

import com.booking.ticketbookingservice.entity.BookingStatus;
import lombok.Data;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Data
public class BookingResponse {
    private Integer bookingId;
    private Integer userId;
    private Instant bookingTime;
    private BigDecimal totalAmount;
    private BookingStatus status;
    private List<TicketResponse> tickets;
}