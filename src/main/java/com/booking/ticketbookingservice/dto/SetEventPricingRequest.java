package com.booking.ticketbookingservice.dto;

import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class SetEventPricingRequest {
    @NotNull
    private Integer eventId;
    @NotNull
    private Integer tierId;
    @NotNull
    private BigDecimal price;
}