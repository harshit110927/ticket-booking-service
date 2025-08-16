package com.booking.ticketbookingservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class TicketBookingServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(TicketBookingServiceApplication.class, args);
    }

}
