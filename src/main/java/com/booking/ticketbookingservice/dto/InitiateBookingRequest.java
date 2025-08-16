package com.booking.ticketbookingservice.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.util.List;

@Data
public class InitiateBookingRequest {
    @NotNull
    private Integer userId; // This field is now required

    @NotEmpty
    private List<Integer> ticketIds;
}