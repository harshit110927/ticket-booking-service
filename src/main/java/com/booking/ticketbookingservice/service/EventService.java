package com.booking.ticketbookingservice.service;

import com.booking.ticketbookingservice.dto.CreateEventRequest;
import com.booking.ticketbookingservice.entity.Event;
import com.booking.ticketbookingservice.repository.EventRepository;
import com.booking.ticketbookingservice.repository.MapRepository;
import com.booking.ticketbookingservice.repository.ShowRepository;
import org.springframework.stereotype.Service;
import com.booking.ticketbookingservice.dto.SetEventPricingRequest;
import com.booking.ticketbookingservice.entity.*;
import com.booking.ticketbookingservice.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
@Service
public class EventService {
    private final EventRepository eventRepository;
    private final ShowRepository showRepository;
    private final MapRepository mapRepository;
    private final EventPricingRepository eventPricingRepository;
    private final PricingTierRepository pricingTierRepository;
    private final SeatRepository seatRepository;
    private final TicketRepository ticketRepository;

    public EventService(EventRepository eventRepository, ShowRepository showRepository, MapRepository mapRepository, EventPricingRepository eventPricingRepository, PricingTierRepository pricingTierRepository, SeatRepository seatRepository, TicketRepository ticketRepository ) {
        this.eventRepository = eventRepository;
        this.showRepository = showRepository;
        this.mapRepository = mapRepository;
        this.eventPricingRepository = eventPricingRepository;
        this.pricingTierRepository = pricingTierRepository;
        this.seatRepository = seatRepository;
        this.ticketRepository = ticketRepository;
    }

    public Event createEvent(CreateEventRequest request) {
        var show = showRepository.findById(request.getShowId()).orElseThrow(() -> new RuntimeException("Show not found"));
        var map = mapRepository.findById(request.getMapId()).orElseThrow(() -> new RuntimeException("Map not found"));

        Event event = new Event();
        event.setShow(show);
        event.setMap(map);
        event.setStartTime(request.getStartTime());
        event.setEndTime(request.getEndTime());

        return eventRepository.save(event);
    }
    public EventPricing setEventPricing(SetEventPricingRequest request) {
        var event = eventRepository.findById(request.getEventId()).orElseThrow(() -> new RuntimeException("Event not found"));
        var tier = pricingTierRepository.findById(request.getTierId()).orElseThrow(() -> new RuntimeException("PricingTier not found"));

        EventPricing eventPricing = new EventPricing();
        eventPricing.setEvent(event);
        eventPricing.setPricingTier(tier);
        eventPricing.setPrice(request.getPrice());
        return eventPricingRepository.save(eventPricing);
    }
    @Transactional
    public List<Ticket> generateTicketsForEvent(Integer eventId) {
        var event = eventRepository.findById(eventId).orElseThrow(() -> new RuntimeException("Event not found"));

        // 1. Get all pricing tiers for the event
        List<EventPricing> eventPricings = eventPricingRepository.findByEvent_EventId(eventId);
        if (eventPricings.isEmpty()) {
            throw new RuntimeException("Pricing must be set for the event before generating tickets.");
        }
        // Create a map for easy lookup: Tier ID -> Price
        Map<Integer, EventPricing> pricingMap = eventPricings.stream()
                .collect(Collectors.toMap(ep -> ep.getPricingTier().getTierId(), ep -> ep));

        // 2. Find all seats for the event's map
        List<Seat> seats = seatRepository.findAllByMapId(event.getMap().getMapId());

        // 3. Create a ticket for each seat
        List<Ticket> tickets = new ArrayList<>();
        for (Seat seat : seats) {
            // NOTE: Simple logic to assign a pricing tier.
            // In a real system, this would be more complex (e.g., section determines tier).
            // For now, we'll just assign the first available pricing tier.
            EventPricing defaultPricing = pricingMap.values().iterator().next();

            Ticket ticket = new Ticket();
            ticket.setEvent(event);
            ticket.setSeat(seat);
            ticket.setPricingTier(defaultPricing.getPricingTier());
            ticket.setPrice(defaultPricing.getPrice());
            ticket.setStatus(TicketStatus.available);
            tickets.add(ticket);
        }

        return ticketRepository.saveAll(tickets);
    }
}