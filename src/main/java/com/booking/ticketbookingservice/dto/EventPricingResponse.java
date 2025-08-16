package com.booking.ticketbookingservice.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class EventPricingResponse {
    private Integer eventPricingId;
    private BigDecimal price;
    private Integer eventId;
    private String tierName;
}