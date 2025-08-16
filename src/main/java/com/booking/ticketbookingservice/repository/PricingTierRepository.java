package com.booking.ticketbookingservice.repository;

import com.booking.ticketbookingservice.entity.PricingTier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PricingTierRepository extends JpaRepository<PricingTier, Integer> {}