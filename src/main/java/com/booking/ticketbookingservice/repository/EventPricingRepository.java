package com.booking.ticketbookingservice.repository;

import com.booking.ticketbookingservice.entity.EventPricing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
//public interface EventPricingRepository extends JpaRepository<EventPricing, Integer> {}
public interface EventPricingRepository extends JpaRepository<EventPricing, Integer> {
    List<EventPricing> findByEvent_EventId(Integer eventId);
}