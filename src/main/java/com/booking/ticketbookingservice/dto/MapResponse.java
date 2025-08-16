package com.booking.ticketbookingservice.dto;

import lombok.Data;

@Data
public class MapResponse {
    private Integer mapId;
    private String mapName;
    private String location;
    private Long tenantId;
}