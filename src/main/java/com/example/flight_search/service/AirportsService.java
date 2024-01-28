package com.example.flight_search.service;

import com.example.flight_search.dto.AirportsDto;
import com.example.flight_search.entity.Airports;
import com.example.flight_search.repo.AirportsRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AirportsService {

    private final AirportsRepo airportsRepo;
    private final ModelMapper modelMapper;
    @Autowired
    public AirportsService(AirportsRepo airportsRepo, ModelMapper modelMapper) {
        this.airportsRepo = airportsRepo;
        this.modelMapper = modelMapper;
    }

    public AirportsDto createAirport(Airports newAirports) {
        Airports airports = airportsRepo.save(newAirports);
        return modelMapper.map(airports, AirportsDto.class);
    }

    public List<AirportsDto> getAll() {
        List<Airports> airports = airportsRepo.findAll();
        return airports.stream().map(airports1 -> modelMapper.map(airports1, AirportsDto.class)).collect(Collectors.toList());
    }

    public void deleteAirport(Long airportId) {
        airportsRepo.deleteById(airportId);
    }

    public AirportsDto updateAirport(Airports updateAirport, Long airportId) {
        Optional<Airports> airports = airportsRepo.findById(airportId);
        if (!airports.isPresent()){
            return null;
        }
        airports.get().setCity(updateAirport.getCity());
        airportsRepo.save(airports.get());
        return modelMapper.map(airports.get(), AirportsDto.class);
    }
}
