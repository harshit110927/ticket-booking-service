package com.booking.ticketbookingservice.controller;

import com.booking.ticketbookingservice.dto.CreateShowRequest;
import com.booking.ticketbookingservice.dto.ShowResponse;
import com.booking.ticketbookingservice.entity.Show;
import com.booking.ticketbookingservice.service.ShowService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/shows")
public class ShowController {
    private final ShowService showService;

    public ShowController(ShowService showService) {
        this.showService = showService;
    }

    @PostMapping
    public ResponseEntity<ShowResponse> createShow(@Valid @RequestBody CreateShowRequest request) {
        Show createdShow = showService.createShow(request);
        return ResponseEntity.ok(toShowResponse(createdShow));
    }

    @GetMapping
    public ResponseEntity<List<ShowResponse>> getAllShows() {
        List<Show> shows = showService.getAllShows();
        List<ShowResponse> showResponses = shows.stream()
                .map(this::toShowResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(showResponses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ShowResponse> getShowById(@PathVariable Integer id) {
        Show show = showService.getShow(id);
        return ResponseEntity.ok(toShowResponse(show));
    }

    private ShowResponse toShowResponse(Show show) {
        ShowResponse dto = new ShowResponse();
        dto.setShowId(show.getShowId());
        dto.setTitle(show.getTitle());
        dto.setDescription(show.getDescription());
        dto.setDurationInMinutes(show.getDurationInMinutes());
        return dto;
    }
}