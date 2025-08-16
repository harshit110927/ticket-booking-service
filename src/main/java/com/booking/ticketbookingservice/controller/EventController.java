package com.booking.ticketbookingservice.controller;

import com.booking.ticketbookingservice.dto.*;
import com.booking.ticketbookingservice.entity.Event;
import com.booking.ticketbookingservice.entity.EventPricing;
import com.booking.ticketbookingservice.entity.Ticket;
import com.booking.ticketbookingservice.service.EventService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/events")
public class EventController {
    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @PostMapping
    public ResponseEntity<EventSummaryDto> createEvent(@Valid @RequestBody CreateEventRequest request) {
        Event event = eventService.createEvent(request);
        return ResponseEntity.ok(toEventSummaryDto(event));
    }

    @PostMapping("/pricing")
    public ResponseEntity<EventPricingResponse> setEventPricing(@Valid @RequestBody SetEventPricingRequest request) {
        EventPricing eventPricing = eventService.setEventPricing(request);
        return ResponseEntity.ok(toEventPricingResponse(eventPricing));
    }

    @PostMapping("/{eventId}/generate-tickets")
    public ResponseEntity<List<TicketResponse>> generateTickets(@PathVariable Integer eventId) {
        List<Ticket> tickets = eventService.generateTicketsForEvent(eventId);
        List<TicketResponse> ticketResponses = tickets.stream()
                .map(this::toTicketResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(ticketResponses);
    }

    // --- Helper Conversion Methods ---

    private TicketResponse toTicketResponse(Ticket ticket) {
        TicketResponse dto = new TicketResponse();
        dto.setTicketId(ticket.getTicketId());
        dto.setPrice(ticket.getPrice());
        dto.setStatus(ticket.getStatus());
        dto.setLockedUntil(ticket.getLockedUntil());
        dto.setEvent(toEventSummaryDto(ticket.getEvent()));

        SeatResponse seatDto = new SeatResponse();
        seatDto.setSeatId(ticket.getSeat().getSeatId());
        seatDto.setRowIdentifier(ticket.getSeat().getRowIdentifier());
        seatDto.setSeatNumber(ticket.getSeat().getSeatNumber());
        dto.setSeat(seatDto);

        return dto;
    }

    private EventSummaryDto toEventSummaryDto(Event event) {
        EventSummaryDto dto = new EventSummaryDto();
        dto.setEventId(event.getEventId());
        dto.setStartTime(event.getStartTime());
        dto.setEndTime(event.getEndTime());
        dto.setShowId(event.getShow().getShowId());
        dto.setMapId(event.getMap().getMapId());
        return dto;
    }

    private EventPricingResponse toEventPricingResponse(EventPricing eventPricing) {
        EventPricingResponse dto = new EventPricingResponse();
        dto.setEventPricingId(eventPricing.getEventPricingId());
        dto.setPrice(eventPricing.getPrice());
        dto.setEventId(eventPricing.getEvent().getEventId());
        dto.setTierName(eventPricing.getPricingTier().getTierName());
        return dto;
    }
}