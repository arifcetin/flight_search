package com.example.flight_search.service;

import com.example.flight_search.dto.FlightsDto;
import com.example.flight_search.entity.Flights;
import com.example.flight_search.repo.FlightsRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class FlightsService {

    private final FlightsRepo flightsRepo;
    private final ModelMapper modelMapper;
    @Autowired
    public FlightsService(FlightsRepo flightsRepo, ModelMapper modelMapper) {
        this.flightsRepo = flightsRepo;
        this.modelMapper = modelMapper;
    }

    public FlightsDto createFlight(Flights newFlight) {
        Flights flights = flightsRepo.save(newFlight);
        return modelMapper.map(flights, FlightsDto.class);
    }

    public List<FlightsDto> getAllFlights() {
        List<Flights> flights = flightsRepo.findAll();
        return flights.stream().map(flights1 -> modelMapper.map(flights1, FlightsDto.class)).collect(Collectors.toList());
    }


    public List<FlightsDto> getOneFlight(Long flightId) {
        Optional<Flights> flights = flightsRepo.findById(flightId);
        if (!flights.isPresent()){
            return null;
        }
        List<Flights> flightsList = new ArrayList<>();
        flightsList.add(flights.get());
        if(flights.get().getReturn_date()!= null){
            Flights flights1 = new Flights();
            flights1.setArrival(flights.get().getDeparture());
            flights1.setDeparture(flights.get().getArrival());
            flights1.setDeparture_date(flights.get().getReturn_date());
            flights1.setPrice(flights.get().getPrice());
            flightsList.add(flights1);
        }
        return flightsList.stream().map(flights2 -> modelMapper.map(flights2, FlightsDto.class)).collect(Collectors.toList());
    }

    public FlightsDto saveFlight(FlightsDto flightDto) {
        Flights flight = modelMapper.map(flightDto, Flights.class);
        Flights savedFlight = flightsRepo.save(flight);
        return modelMapper.map(savedFlight, FlightsDto.class);
    }

    public List<FlightsDto> searchFlights(String departure, String arrival, LocalDateTime departureDate, LocalDateTime returnDate) {
        List<Flights> matchingFlights;

        if (returnDate != null) {
            // Eğer returnDate belirtilmişse, gidiş ve dönüş tarihlerine göre uçuşları filtrele
            matchingFlights = flightsRepo.findByDepartureAndArrivalAndDeparture_dateAndReturn_date(departure, arrival, departureDate, returnDate);
        } else {
            // Eğer returnDate belirtilmemişse, sadece gidiş tarihine göre uçuşları filtrele
            matchingFlights = flightsRepo.findByDepartureAndArrivalAndDeparture_date(departure, arrival, departureDate);
        }

        return matchingFlights.stream()
                .map(flight -> modelMapper.map(flight, FlightsDto.class))
                .collect(Collectors.toList());
    }

    public FlightsDto updateFlight(Flights updateFlights, Long flightId) {
        Optional<Flights> flights = flightsRepo.findById(flightId);
        if (!flights.isPresent()){
            return null;
        }
        flights.get().setDeparture(updateFlights.getDeparture());
        flights.get().setArrival(updateFlights.getArrival());
        flights.get().setDeparture_date(updateFlights.getDeparture_date());
        flights.get().setReturn_date((updateFlights.getReturn_date() != null) ? updateFlights.getReturn_date() : null);
        flights.get().setPrice(updateFlights.getPrice());
        flightsRepo.save(flights.get());
        return modelMapper.map(flights.get(), FlightsDto.class);
    }

    public void deleteFlight(Long flightId) {
        flightsRepo.deleteById(flightId);
    }
}
