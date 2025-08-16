package com.booking.ticketbookingservice.service;

import com.booking.ticketbookingservice.dto.InitiateBookingRequest;
import com.booking.ticketbookingservice.entity.*;
import com.booking.ticketbookingservice.repository.BookedTicketRepository;
import com.booking.ticketbookingservice.repository.BookingRepository;
import com.booking.ticketbookingservice.repository.TicketRepository;
import com.booking.ticketbookingservice.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class BookingService {
    private final BookingRepository bookingRepository;
    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;
    private final BookedTicketRepository bookedTicketRepository;

    public BookingService(BookingRepository bookingRepository, TicketRepository ticketRepository, UserRepository userRepository, BookedTicketRepository bookedTicketRepository) {
        this.bookingRepository = bookingRepository;
        this.ticketRepository = ticketRepository;
        this.userRepository = userRepository;
        this.bookedTicketRepository = bookedTicketRepository;
    }

    @Transactional
    public Booking initiateBooking(InitiateBookingRequest request) {
        // Get the user by the ID passed in the request
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + request.getUserId()));

        List<Ticket> ticketsToBook = ticketRepository.findAllById(request.getTicketIds());

        if (ticketsToBook.size() != request.getTicketIds().size()) {
            throw new RuntimeException("One or more tickets not found.");
        }

        BigDecimal totalAmount = BigDecimal.ZERO;
        for (Ticket ticket : ticketsToBook) {
            if (ticket.getStatus() != TicketStatus.available) {
                throw new RuntimeException("Ticket ID " + ticket.getTicketId() + " is not available for booking.");
            }
            ticket.setStatus(TicketStatus.locked);
            ticket.setLockedUntil(Instant.now().plus(10, ChronoUnit.MINUTES));
            totalAmount = totalAmount.add(ticket.getPrice());
        }

        ticketRepository.saveAll(ticketsToBook);

        Booking booking = new Booking();
        booking.setUser(user);
        booking.setTotalAmount(totalAmount);
        booking.setStatus(BookingStatus.pending);
        Booking savedBooking = bookingRepository.save(booking);

        for (Ticket ticket : ticketsToBook) {
            BookedTicket bookedTicket = new BookedTicket();
            bookedTicket.setBooking(savedBooking);
            bookedTicket.setTicket(ticket);
            bookedTicketRepository.save(bookedTicket);
        }

        return savedBooking;
    }
}