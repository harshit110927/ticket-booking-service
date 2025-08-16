package com.booking.ticketbookingservice.controller;

import com.booking.ticketbookingservice.dto.*;
import com.booking.ticketbookingservice.entity.BookedTicket;
import com.booking.ticketbookingservice.entity.Booking;
import com.booking.ticketbookingservice.repository.BookedTicketRepository;
import com.booking.ticketbookingservice.service.BookingService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/bookings")
public class BookingController {
    private final BookingService bookingService;
    private final BookedTicketRepository bookedTicketRepository; // Injected to fetch tickets for the response

    public BookingController(BookingService bookingService, BookedTicketRepository bookedTicketRepository) {
        this.bookingService = bookingService;
        this.bookedTicketRepository = bookedTicketRepository;
    }

    @PostMapping("/initiate")
    public ResponseEntity<BookingResponse> initiateBooking(@Valid @RequestBody InitiateBookingRequest request) {
        Booking booking = bookingService.initiateBooking(request);
        return ResponseEntity.ok(toBookingResponse(booking));
    }

    // --- Helper DTO Conversion Methods ---

    private BookingResponse toBookingResponse(Booking booking) {
        BookingResponse dto = new BookingResponse();
        dto.setBookingId(booking.getBookingId());
        dto.setUserId(booking.getUser().getUserId());
        dto.setBookingTime(booking.getBookingTime());
        dto.setTotalAmount(booking.getTotalAmount());
        dto.setStatus(booking.getStatus());

        List<BookedTicket> bookedTickets = bookedTicketRepository.findByBooking_BookingId(booking.getBookingId());
        List<TicketResponse> ticketResponses = bookedTickets.stream()
                .map(bookedTicket -> toTicketResponse(bookedTicket.getTicket()))
                .collect(Collectors.toList());
        dto.setTickets(ticketResponses);

        return dto;
    }

    private TicketResponse toTicketResponse(com.booking.ticketbookingservice.entity.Ticket ticket) {
        TicketResponse dto = new TicketResponse();
        dto.setTicketId(ticket.getTicketId());
        dto.setPrice(ticket.getPrice());
        dto.setStatus(ticket.getStatus());
        dto.setLockedUntil(ticket.getLockedUntil());

        // Create a summary of the seat to avoid deep nesting
        SeatResponse seatDto = new SeatResponse();
        seatDto.setSeatId(ticket.getSeat().getSeatId());
        seatDto.setRowIdentifier(ticket.getSeat().getRowIdentifier());
        seatDto.setSeatNumber(ticket.getSeat().getSeatNumber());
        dto.setSeat(seatDto);

        return dto;
    }
}