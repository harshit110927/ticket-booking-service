package com.booking.ticketbookingservice.service;

import com.booking.ticketbookingservice.dto.CreateShowRequest;
import com.booking.ticketbookingservice.entity.Show;
import com.booking.ticketbookingservice.repository.ShowRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ShowService {
    private final ShowRepository showRepository;

    public ShowService(ShowRepository showRepository) {
        this.showRepository = showRepository;
    }

    public Show createShow(CreateShowRequest request) {
        Show show = new Show();
        show.setTitle(request.getTitle());
        show.setDescription(request.getDescription());
        show.setDurationInMinutes(request.getDurationInMinutes());
        return showRepository.save(show);
    }

    public List<Show> getAllShows() {
        return showRepository.findAll();
    }

    public Show getShow(Integer id) {
        return showRepository.findById(id).orElseThrow(() -> new RuntimeException("Show not found"));
    }
}