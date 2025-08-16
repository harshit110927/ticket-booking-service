package com.booking.ticketbookingservice.repository;

import com.booking.ticketbookingservice.entity.BookedTicket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface BookedTicketRepository extends JpaRepository<BookedTicket, Integer> {
    List<BookedTicket> findByBooking_BookingId(Integer bookingId);
}