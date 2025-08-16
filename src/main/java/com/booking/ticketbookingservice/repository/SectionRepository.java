package com.booking.ticketbookingservice.repository;

import com.booking.ticketbookingservice.entity.Section;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SectionRepository extends JpaRepository<Section, Integer> {}