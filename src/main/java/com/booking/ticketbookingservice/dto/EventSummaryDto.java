package com.booking.ticketbookingservice.dto;

import lombok.Data;
import java.time.Instant;

@Data
public class EventSummaryDto {
    private Integer eventId;
    private Instant startTime;
    private Instant endTime;
    private Integer showId;
    private Integer mapId;
}