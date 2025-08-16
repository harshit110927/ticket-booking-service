package com.booking.ticketbookingservice.repository;

import com.booking.ticketbookingservice.entity.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
@Repository
public interface SeatRepository extends JpaRepository<Seat, Integer> {
    @Query("SELECT s FROM Seat s WHERE s.section.map.mapId = :mapId")
    List<Seat> findAllByMapId(Integer mapId);
}