package com.booking.ticketbookingservice.controller;

import com.booking.ticketbookingservice.dto.*;
import com.booking.ticketbookingservice.entity.Map;
import com.booking.ticketbookingservice.entity.PricingTier;
import com.booking.ticketbookingservice.entity.Section;
import com.booking.ticketbookingservice.entity.Seat;
import com.booking.ticketbookingservice.service.VenueSetupService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/venue")
public class VenueSetupController {
    private final VenueSetupService venueSetupService;

    public VenueSetupController(VenueSetupService venueSetupService) {
        this.venueSetupService = venueSetupService;
    }

    @PostMapping("/maps")
    public ResponseEntity<MapResponse> createMap(@Valid @RequestBody CreateMapRequest request) {
        Map savedMap = venueSetupService.createMap(request);
        return ResponseEntity.ok(toMapResponse(savedMap));
    }

    @PostMapping("/sections")
    public ResponseEntity<SectionResponse> createSection(@Valid @RequestBody CreateSectionRequest request) {
        Section savedSection = venueSetupService.createSection(request);
        return ResponseEntity.ok(toSectionResponse(savedSection));
    }

    @PostMapping("/seats")
    public ResponseEntity<SeatResponse> createSeat(@Valid @RequestBody CreateSeatRequest request) {
        Seat savedSeat = venueSetupService.createSeat(request);
        return ResponseEntity.ok(toSeatResponse(savedSeat));
    }

    @PostMapping("/pricing-tiers")
    public ResponseEntity<PricingTierResponse> createPricingTier(@Valid @RequestBody CreatePricingTierRequest request) {
        PricingTier savedTier = venueSetupService.createPricingTier(request);
        return ResponseEntity.ok(toPricingTierResponse(savedTier));
    }

    // --- Helper Conversion Methods ---

    private MapResponse toMapResponse(Map map) {
        MapResponse dto = new MapResponse();
        dto.setMapId(map.getMapId());
        dto.setMapName(map.getMapName());
        dto.setLocation(map.getLocation());
        dto.setTenantId(map.getTenant().getTenantId());
        return dto;
    }

    private SectionResponse toSectionResponse(Section section) {
        SectionResponse dto = new SectionResponse();
        dto.setSectionId(section.getSectionId());
        dto.setSectionName(section.getSectionName());
        dto.setMap(toMapResponse(section.getMap()));
        return dto;
    }

    private SeatResponse toSeatResponse(Seat seat) {
        SeatResponse dto = new SeatResponse();
        dto.setSeatId(seat.getSeatId());
        dto.setRowIdentifier(seat.getRowIdentifier());
        dto.setSeatNumber(seat.getSeatNumber());
        return dto;
    }

    private PricingTierResponse toPricingTierResponse(PricingTier pricingTier) {
        PricingTierResponse dto = new PricingTierResponse();
        dto.setTierId(pricingTier.getTierId());
        dto.setTierName(pricingTier.getTierName());
        dto.setTenantId(pricingTier.getTenant().getTenantId());
        return dto;
    }
}