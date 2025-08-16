package com.booking.ticketbookingservice.service;

import com.booking.ticketbookingservice.dto.CreateMapRequest;
import com.booking.ticketbookingservice.dto.CreatePricingTierRequest;
import com.booking.ticketbookingservice.dto.CreateSectionRequest;
import com.booking.ticketbookingservice.dto.CreateSeatRequest;
import com.booking.ticketbookingservice.entity.Map;
import com.booking.ticketbookingservice.entity.Section;
import com.booking.ticketbookingservice.entity.Seat;
import com.booking.ticketbookingservice.repository.*;
import org.springframework.stereotype.Service;
import com.booking.ticketbookingservice.entity.PricingTier;

@Service
public class VenueSetupService {
    private final MapRepository mapRepository;
    private final SectionRepository sectionRepository;
    private final SeatRepository seatRepository;
    private final TenantRepository tenantRepository;
    private final PricingTierRepository pricingTierRepository;

    public VenueSetupService(MapRepository mapRepository, SectionRepository sectionRepository, SeatRepository seatRepository, TenantRepository tenantRepository, PricingTierRepository pricingTierRepository) {
        this.mapRepository = mapRepository;
        this.sectionRepository = sectionRepository;
        this.seatRepository = seatRepository;
        this.tenantRepository = tenantRepository;
        this.pricingTierRepository = pricingTierRepository;
    }

    public Map createMap(CreateMapRequest request) {
        var tenant = tenantRepository.findById(request.getTenantId())
                .orElseThrow(() -> new RuntimeException("Tenant not found"));
        Map map = new Map();
        map.setTenant(tenant);
        map.setMapName(request.getMapName());
        map.setLocation(request.getLocation());
        return mapRepository.save(map);
    }

    public Section createSection(CreateSectionRequest request) {
        var map = mapRepository.findById(request.getMapId())
                .orElseThrow(() -> new RuntimeException("Map not found"));
        Section section = new Section();
        section.setMap(map);
        section.setSectionName(request.getSectionName());
        return sectionRepository.save(section);
    }

    public Seat createSeat(CreateSeatRequest request) {
        var section = sectionRepository.findById(request.getSectionId())
                .orElseThrow(() -> new RuntimeException("Section not found"));
        Seat seat = new Seat();
        seat.setSection(section);
        seat.setRowIdentifier(request.getRowIdentifier());
        seat.setSeatNumber(request.getSeatNumber());
        return seatRepository.save(seat);
    }
    public PricingTier createPricingTier(CreatePricingTierRequest request) {
        var tenant = tenantRepository.findById(request.getTenantId())
                .orElseThrow(() -> new RuntimeException("Tenant not found"));

        PricingTier pricingTier = new PricingTier();
        pricingTier.setTenant(tenant);
        pricingTier.setTierName(request.getTierName());

        return pricingTierRepository.save(pricingTier);
    }
}