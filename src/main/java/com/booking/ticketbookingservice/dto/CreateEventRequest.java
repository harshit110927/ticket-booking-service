package com.booking.ticketbookingservice.dto;

import jakarta.validation.constraints.NotNull;
import java.time.Instant;
import lombok.Data;

@Data
public class CreateEventRequest {
    @NotNull
    private Integer showId;
    @NotNull
    private Integer mapId;
    @NotNull
    private Instant startTime;
    @NotNull
    private Instant endTime;
}