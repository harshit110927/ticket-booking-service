package com.booking.ticketbookingservice.dto;

import lombok.Data;

@Data
public class SectionResponse {
    private Integer sectionId;
    private String sectionName;
    private MapResponse map;
}