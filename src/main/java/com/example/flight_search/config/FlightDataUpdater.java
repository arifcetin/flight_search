package com.example.flight_search.config;

import com.example.flight_search.dto.FlightsDto;
import com.example.flight_search.service.FlightsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class FlightDataUpdater {
    private final FlightsService flightsService;

    @Autowired
    public FlightDataUpdater(FlightsService flightsService) {
        this.flightsService = flightsService;
    }

    @Scheduled(cron = "1 * * * * *")
    public void updateFlightData() {
        FlightsDto mockFlightDto = new FlightsDto();
        mockFlightDto.setDeparture("IST");
        mockFlightDto.setArrival("JFK");
        mockFlightDto.setDeparture_date(LocalDateTime.now());
        mockFlightDto.setReturn_date(LocalDateTime.now().plusDays(3));
        mockFlightDto.setPrice(500.0);

        FlightsDto savedFlight = flightsService.saveFlight(mockFlightDto);
    }
}