package com.booking.ticketbookingservice.repository;

import com.booking.ticketbookingservice.entity.Map;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MapRepository extends JpaRepository<Map, Integer> {}